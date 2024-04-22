package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.controller.utils.ReminderUtils;
import edu.monash.bridgingculture.intf.FestivalService;
import edu.monash.bridgingculture.intf.mapper.FestivalMapper;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.ChatMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class FestivalServiceImpl implements FestivalService {

    @Resource
    Environment env;
    @Resource
    OpenAiChatClient chatClient;
    @Resource
    HttpUtil httpUtil;
    @Resource
    JavaMailSender mailSender;
    @Resource
    ReminderUtils reminderUtils;
    @Resource
    FestivalMapper festivalMapper;
    static final String CHARSET = "UTF-8";
    ConcurrentHashMap<String, ConcurrentLinkedDeque<Long>> trafficMap = new ConcurrentHashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public int isAllowed(String userIP) {
        ConcurrentLinkedDeque<Long> timestamps = trafficMap.computeIfAbsent(userIP, k -> new ConcurrentLinkedDeque<>());
        long current = System.currentTimeMillis();
        cleanOldTimestamps(timestamps, current);
        if (timestamps.size() < 5) {
            timestamps.addLast(current);
            return 0;
        } else {
            long oldestTimestamp = timestamps.getFirst();
            int diff = (int) ((current - oldestTimestamp) / 1000); // seconds
            return Math.max(0, 60 - diff);
        }
    }

    @Override
    public String chatBot(HttpServletRequest request, HttpServletResponse response, String query) {
        Cookie[] cookies = request.getCookies();
        List<Message> messages = new ArrayList<>();
        if(cookies != null){
            int i = 1;
            for(Cookie cookie: cookies){
                if(cookie.getValue() != null){
                    String value = new String(Base64.getDecoder().decode(cookie.getValue()));
                    if(i % 2 == 1){
                        messages.add(new ChatMessage(MessageType.USER, value));
                    }else{
                        messages.add(new ChatMessage(MessageType.ASSISTANT, value));
                    }
                    log.info(i + " " + value);
                    i++;
                }
            }
        }

        messages.add(new ChatMessage(MessageType.USER, query));
        messages.add(new ChatMessage(MessageType.SYSTEM, env.getProperty("OPENAI_system_content")));
        ChatResponse call = chatClient.call(new Prompt(messages));
        String res = call.getResult().getOutput().getContent();

        if(cookies != null && cookies.length >= 10){
            removeCookie(cookies[0], response);
            removeCookie(cookies[1], response);
        }
        long currentTime = System.currentTimeMillis();
        setCookie(query, currentTime, response);
        setCookie(res,currentTime + 1, response);
        return res;
    }

    @Override
    public List<Festival> getFestivals(List<String> countries, int year, int month, @Nullable List<String> types) {
        List<Festival> festivals = festivalMapper.getFestival(countries, year);
        if(month != -1)
            fitterByMonth(festivals, month);
        if(types != null)
            fitterByType(festivals, types);
        return festivals;
    }

    @Override
    public ResponseDO createReminder(Reminder.RequestDO reminderRequest) {
        Festival festival = festivalMapper.getFestivalById(reminderRequest.getId());
        if(festival == null)
            return ResponseDO.fail("Invalid festival.");

        int number; // Ahead Number
        try{
            number = Integer.parseInt(reminderRequest.getAheadNumber());
        }catch (Exception e){
            return ResponseDO.fail("Invalid ahead number.");
        }
        int aheadHours = number * reminderRequest.getUnitType().getAheadHours();
        LocalDate localDate = LocalDate.parse(festival.getDate(), formatter);
        LocalDateTime festivalDate = localDate.atStartOfDay();
        LocalDateTime reminderTime = festivalDate.minus(aheadHours, ChronoUnit.HOURS);

        executorService.submit(() -> {
            String htmlContent = reminderUtils.buildHTMLContent(festival, reminderRequest);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            reminderUtils.fillMessage(
                    mimeMessage,
                    env.getProperty("spring.mail.username"),
                    reminderRequest,
                    festival,
                    htmlContent,
                    reminderTime);

            mailSender.send(mimeMessage);
            log.info("Send an email to " + reminderRequest.getEmail() + " at " + reminderTime.format(formatterWithTime));
        });

        return ResponseDO.success("We will send an email to you at " + reminderTime.format(formatterWithTime) + ".");
    }

    @Override
    public ResponseDO addFestivals(List<String> countries, int year) {
        CountDownLatch countDownLatch = new CountDownLatch(countries.size());
        for(String country: countries){
            executorService.submit(() -> {
                List<Festival> festivals = httpUtil.getFestivalByCountry(country, year, null);
                festivals.sort(Comparator.comparing(Festival::getDate));
                for(Festival festival: festivals)
                    festivalMapper.addFestival(festival);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseDO.success("");
    }

    public void fitterByMonth(List<Festival> festivals, int month){
        Iterator<Festival> iterator = festivals.iterator();
        while(iterator.hasNext()){
            Festival festival = iterator.next();
            LocalDate date = LocalDate.parse(festival.getDate(), formatter);
            if(date.getMonthValue() != month)
                iterator.remove();
        }
    }

    public void fitterByType(List<Festival> festivals, List<String> types){
        Set<String> set = new HashSet<>(types);
        festivals.removeIf(festival -> !set.contains(festival.getType()));
    }

    private void setCookie(String content,long time, HttpServletResponse response){
        String encode;
        try {
            encode = Base64.getEncoder().encodeToString(content.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Cookie cookie = new Cookie(String.valueOf(time), encode);
        cookie.setDomain(env.getProperty("OPENAI_cookie_domain"));
        cookie.setMaxAge(24*60*60);
        response.addCookie(cookie);
    }

    private void removeCookie(Cookie cookie, HttpServletResponse response){
        Cookie newCookie = new Cookie(cookie.getName(), null);
        newCookie.setMaxAge(0);
        response.addCookie(newCookie);
    }

    @Scheduled(fixedDelay = 1000L*10)
    public void checkExpiredRecord(){
        for(Map.Entry<String, ConcurrentLinkedDeque<Long> > e: trafficMap.entrySet()){
            ConcurrentLinkedDeque<Long> timestamps = e.getValue();
            long current = System.currentTimeMillis();
            cleanOldTimestamps(timestamps, current);
        }
    }

    private void cleanOldTimestamps(ConcurrentLinkedDeque<Long> timestamps, long current) {
        while (!timestamps.isEmpty() && current - timestamps.getFirst() >= 60 * 1000) {
            timestamps.removeFirst();
        }
    }
}
