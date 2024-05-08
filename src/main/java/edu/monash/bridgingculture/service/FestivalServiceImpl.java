package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.controller.utils.ReminderUtils;
import edu.monash.bridgingculture.intf.FestivalService;
import edu.monash.bridgingculture.intf.mapper.FestivalMapper;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.ChatBotHistory;
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

import static edu.monash.bridgingculture.service.utils.ThreadPoolUtils.executorService;

@Service
@Slf4j
public class FestivalServiceImpl implements FestivalService {

    @Resource
    Environment env;
    @Resource
    HttpUtil httpUtil;
    @Resource
    OpenAiChatClient chatClient;
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

    /**
     * Checks whether the user with the provided IP address is allowed to make a request.
     *
     * @param userIP The IP address of the user.
     * @return An integer representing the number of seconds the user needs to wait before making another request,
     *         or 0 if the user is allowed to make a request.
     */
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

    /**
     * Handles the chatbot interaction.
     *
     * @param request  The HttpServletRequest object representing the incoming request.
     * @param response The HttpServletResponse object representing the outgoing response.
     * @param query    The user's query.
     * @return The response from the chatbot.
     */
//    @Override
//    public String chatBot(HttpServletRequest request, HttpServletResponse response, String query) {
//        Cookie[] cookies = request.getCookies();
//        List<Message> messages = new ArrayList<>();
//        if(cookies != null){
//            int i = 1;
//            for(Cookie cookie: cookies){
//                if(cookie.getValue() != null){
//                    String value = new String(Base64.getDecoder().decode(cookie.getValue()));
//                    if(i % 2 == 1){
//                        messages.add(new ChatMessage(MessageType.USER, value));
//                    }else{
//                        messages.add(new ChatMessage(MessageType.ASSISTANT, value));
//                    }
//                    log.info(i + " " + value);
//                    i++;
//                }
//            }
//        }
//
//        messages.add(new ChatMessage(MessageType.USER, query));
//        messages.add(new ChatMessage(MessageType.SYSTEM, env.getProperty("OPENAI_system_content")));
//        ChatResponse call = chatClient.call(new Prompt(messages));
//        String res = call.getResult().getOutput().getContent();
//
//        if(cookies != null && cookies.length >= 10){
//            removeCookie(cookies[0], response);
//            removeCookie(cookies[1], response);
//        }
//        long currentTime = System.currentTimeMillis();
//        setCookie(query, currentTime, response, request);
//        setCookie(res,currentTime + 1, response, request);
//        return res;
//    }

    @Override
    public String chatBot(HttpServletRequest request, HttpServletResponse response, String query) {
        String ip = request.getRemoteAddr();
        List<ChatBotHistory> histories = festivalMapper.getHistory(ip);
        List<Message> messages = new ArrayList<>();

        if(histories != null){
            Collections.reverse(histories);
            for(ChatBotHistory history: histories){
                if(history.getRole().equals(MessageType.USER.getValue())){
                    messages.add(new ChatMessage(MessageType.USER, history.getContent()));
                }else{
                    messages.add(new ChatMessage(MessageType.ASSISTANT, history.getContent()));
                }
                log.info(MessageType.USER.getValue() + ": " + history.getContent());
            }
        }

        messages.add(new ChatMessage(MessageType.USER, query));
        messages.add(new ChatMessage(MessageType.SYSTEM, env.getProperty("OPENAI_system_content")));
        ChatResponse call = chatClient.call(new Prompt(messages));
        String res = call.getResult().getOutput().getContent();
        if(!res.startsWith(env.getProperty("OPENAI_refuse_flag")))
            executorService.submit(() ->{
                long currentTime = System.currentTimeMillis();
                festivalMapper.addHistory(new ChatBotHistory(ip, MessageType.USER.getValue(), query, currentTime));
                festivalMapper.addHistory(new ChatBotHistory(ip, MessageType.ASSISTANT.getValue(), res, currentTime+1));
            });

        return res;
    }

    /**
     * Retrieves festivals based on the provided criteria.
     *
     * @param countries The list of countries for which festivals are to be retrieved.
     * @param year      The year for which festivals are to be retrieved.
     * @param month     The month for which festivals are to be retrieved. Pass -1 if all months are required.
     * @param types     The types of festivals to filter by. Pass null if no filtering by types is needed.
     * @return A list of Festival objects representing the retrieved festivals.
     */
    @Override
    public List<Festival> getFestivals(List<String> countries, int year, int month, @Nullable List<String> types) {
        List<Festival> festivals = festivalMapper.getFestival(countries, year);
//        List<Festival> festivals = httpUtil.getFestivalByCountry(countries.get(0), year, null);
        if(month != -1)
            fitterByMonth(festivals, month);
        if(types != null)
            fitterByType(festivals, types);
        festivals.sort(Comparator.comparing(Festival::getDate));
        return festivals;
    }

    /**
     * Creates a reminder for a festival.
     *
     * @param reminderRequest The RequestDO object containing the reminder request data.
     * @return A ResponseDO object indicating the result of the reminder creation operation.
     */
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

    /**
     * Adds festivals to the database for the specified countries, year, and month.
     *
     * @param countries The list of countries for which festivals are to be added.
     * @param year      The year for which festivals are to be added.
     * @param month     The month for which festivals are to be added.
     * @return A ResponseDO object indicating the result of the operation.
     */
    @Override
    public ResponseDO addFestivals(List<String> countries, int year, int month) {
        List<Festival> festivals = getFestivals(countries, year, month, null);
        festivals.sort(Comparator.comparing(Festival::getDate));
        for(Festival festival: festivals)
            festivalMapper.addFestival(festival);
        return ResponseDO.success("");
    }

    /**
     * Filters festivals by month.
     *
     * @param festivals The list of festivals to filter.
     * @param month     The month to filter by.
     */
    public void fitterByMonth(List<Festival> festivals, int month){
        Iterator<Festival> iterator = festivals.iterator();
        while(iterator.hasNext()){
            Festival festival = iterator.next();
            LocalDate date = LocalDate.parse(festival.getDate(), formatter);
            if(date.getMonthValue() != month)
                iterator.remove();
        }
    }

    /**
     * Filters festivals by type.
     *
     * @param festivals The list of festivals to filter.
     * @param types     The list of types to filter by.
     */
    public void fitterByType(List<Festival> festivals, List<String> types){
        Set<String> set = new HashSet<>(types);
        festivals.removeIf(festival -> !set.contains(festival.getType()));
    }

    /**
     * Sets a cookie with the provided content, expiration time, and domain in the HttpServletResponse.
     *
     * @param content  The content to be stored in the cookie.
     * @param time     The expiration time for the cookie.
     * @param response The HttpServletResponse object to which the cookie will be added.
     * @param request  The HttpServletRequest object from which the server name will be obtained for setting the cookie domain.
     */
    private void setCookie(String content,long time, HttpServletResponse response, HttpServletRequest request){
        String encode;
        try {
            encode = Base64.getEncoder().encodeToString(content.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Cookie cookie = new Cookie(String.valueOf(time), encode);
        cookie.setDomain(request.getServerName());
//        cookie.setMaxAge(24*60*60);
        // MaxAge: default value is -1 - Session
        response.addCookie(cookie);
    }

    /**
     * Removes a cookie from the HttpServletResponse.
     *
     * @param cookie   The cookie to be removed.
     * @param response The HttpServletResponse object from which the cookie will be removed.
     */
    private void removeCookie(Cookie cookie, HttpServletResponse response){
        Cookie newCookie = new Cookie(cookie.getName(), null);
        newCookie.setMaxAge(0);
        response.addCookie(newCookie);
    }

    /**
     * Periodically checks and removes expired records from the traffic map.
     * This method is scheduled to run with a fixed delay of 10 seconds.
     */
    @Scheduled(fixedDelay = 1000L*10)
    public void checkExpiredRecord(){
        long current = System.currentTimeMillis();
        for(Map.Entry<String, ConcurrentLinkedDeque<Long> > e: trafficMap.entrySet()){
            ConcurrentLinkedDeque<Long> timestamps = e.getValue();
            cleanOldTimestamps(timestamps, current);
        }
        festivalMapper.removeHistory(current - 24*60*60*1000);
    }

    /**
     * Removes old timestamps from the given deque.
     *
     * @param timestamps The deque containing timestamps to be cleaned.
     * @param current    The current time, used to determine whether a timestamp is old.
     */
    private void cleanOldTimestamps(ConcurrentLinkedDeque<Long> timestamps, long current) {
        while (!timestamps.isEmpty() && current - timestamps.getFirst() >= 60 * 1000) {
            timestamps.removeFirst();
        }
    }
}
