package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.controller.utils.ReminderUtils;
import edu.monash.bridgingculture.intf.FestivalService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    static final String CHARSET = "UTF-8";
    ConcurrentHashMap<String, ConcurrentLinkedDeque<Long>> trafficMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Festival> festivalMap = new ConcurrentHashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatterWithSec = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

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
    public List<Festival> getFestival(String country, int year, int month, @Nullable String type) {
        List<Festival> festivals = httpUtil.getFestivalByCountry(country, year, type);
        festivals.sort(Comparator.comparing(Festival::getDate));
        if(month != 0)
            fitterByMonth(festivals, month);
        for(Festival festival: festivals)
            festivalMap.putIfAbsent(festival.getName(), festival);

        return festivals;
    }

    @Override
    public ResponseDO createReminder(Reminder.RequestDO reminderRequest) throws Exception {
        Festival festival = festivalMap.get(reminderRequest.getFestivalName());
        LocalDate localDate = LocalDate.parse(festival.getDate(), formatter);
        LocalDateTime festivalDate = localDate.atStartOfDay();

        int number = 0;
        try{
            number = Integer.parseInt(reminderRequest.getAheadNumber());
        }catch (Exception e){
            return ResponseDO.fail("Invalid number");
        }
        int aheadHours = number * reminderRequest.getUnitType().getAheadHours();
        LocalDateTime reminderTime = festivalDate.minus(aheadHours, ChronoUnit.HOURS);

        String htmlContent = reminderUtils.buildHTMLContent(reminderRequest.getFestivalName(),
                festival.getDate(), festival.getCountry() + festival.getDate());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        reminderUtils.fillMessage(mimeMessage,
                env.getProperty("spring.mail.username"),
                reminderRequest.getEmail(),
                htmlContent,
                reminderTime);

        mailSender.send(mimeMessage);
        log.info("Send an email to " + reminderRequest.getEmail());
        return ResponseDO.success("Set an reminder.");
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