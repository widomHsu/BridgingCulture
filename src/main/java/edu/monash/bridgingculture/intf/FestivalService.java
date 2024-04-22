package edu.monash.bridgingculture.intf;


import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface FestivalService {

    int isAllowed(String userIP);

    String chatBot(HttpServletRequest request, HttpServletResponse response, String query);

    List<Festival> getFestivals(List<String> countries, int year, int month, @Nullable List<String> type) throws InterruptedException;

    ResponseDO createReminder(Reminder.RequestDO reminderRequest);

    ResponseDO addFestivals(List<String> countries, int year);
}
