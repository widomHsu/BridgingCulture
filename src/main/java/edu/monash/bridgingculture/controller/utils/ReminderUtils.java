package edu.monash.bridgingculture.controller.utils;

import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


import java.io.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ReminderUtils {

    @Resource
    private ResourceLoader resourceLoader;
    String emailBodyContent =
                    "Dear %s,<br><br> \n" +
                            "As the %s approaches, we wanted to share the excitement and details of this special occasion with you. Here's a snapshot of what you can expect:<br><br> \n" +
                            "- Country: %s<br> \n" +
                            "- Day: %s<br> \n" +
                            "- Festival Type: %s<br><br> \n" +
                            "We are looking forward to celebrating this %s with you. Please mark your calendars and prepare for a moment filled with joy and tradition.<br><br> \n" +
                            "Should you have any questions regarding the festivities or need further information, feel free to reach out to us.<br><br> \n" +
                            "Warm wishes,<br><br> \n" +
                            "Wominjeka";

    public boolean checkEmail(String email) {
        String regex = "^[\\w.-]+@([a-z0-9][a-z0-9-]*[a-z0-9]?\\.)+[a-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    public String getEmailName(String email) {
        return email.split("@")[0];
    }

    public MimeMessage fillMessage(MimeMessage message, String sendMail, Reminder.RequestDO reminderRequest, Festival festival, String content, LocalDateTime dateTime) {
        try {
            message.setFrom(new InternetAddress(sendMail, "FourReal", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(reminderRequest.getEmail()));

            message.setSubject("Upcoming Celebrations: " + festival.getName(), "UTF-8");
            message.setContent(content, "text/html;charset=UTF-8");

            message.setSentDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
            message.saveChanges();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return message;
    }

    public String buildHTMLContent(Festival festival, Reminder.RequestDO reminderRequest) {
        festival.setType(festival.getType().toLowerCase().replace("_", " "));

        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = resourceLoader.getResource("classpath:mailTemplate.html").getInputStream()) {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = fileReader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            log.error("Resource file not found", e);
        } catch (IOException e) {
            log.error("Failed to read resource file", e);
        }


        String htmlText = builder.toString();
        String festivalDesc = String.format(emailBodyContent,
                getEmailName(reminderRequest.getEmail()), festival.getName(), festival.getCountry(), festival.getDay(), festival.getType(), festival.getType());
        htmlText = htmlText.replace("{festivalName}", festival.getName());
        htmlText = htmlText.replace("{festivalDate}", festival.getDate());
        htmlText = htmlText.replace("{festivalDesc}", festivalDesc);
        return htmlText;
    }
}
