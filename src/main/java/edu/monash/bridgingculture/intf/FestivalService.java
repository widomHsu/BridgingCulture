package edu.monash.bridgingculture.intf;


import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface FestivalService {

    int isAllowed(String userIP);

    String chatBot(HttpServletRequest request, HttpServletResponse response, String query);

    List<Festival> getFestival(String country, int year, int month, @Nullable String type);

    ResponseDO createReminder(Reminder.RequestDO reminderRequest) throws Exception;
}
