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
        "Hi there!!<br><br>" +
        "We hope this message finds you excited and ready for some festival fun! 🥳 We just wanted to drop you a quick reminder that <strong>%s</strong> is just around the corner!<br><br>" +
        "Date: <strong>%s</strong><br><br>" +
        "It's time to clear your schedule because you won't want to miss out on this unforgettable celebration! Whether you're into music, dance, delicious food, or simply soaking up the vibrant atmosphere, there's something for everyone at <strong>%s</strong>.\uD83C\uDFB6\uD83D\uDC83\uD83C\uDF72\n<br><br>" +
        "If you're curious to learn more about what <strong>%s</strong> has to offer, our festive bot is here to help! Simply reach out anytime for information. 🤖 And don't forget to visit our website for additional insights and inspiration!<br><br>" +
        "So, mark your calendar, set your alarms, and get ready to make some amazing memories at <strong>%s</strong>\uD83C\uDFAA\n!<br><br>" +
        "Cheers,<br>" +
        "The Wominjeka Team";

    public boolean checkEmail(String email) {
        String regex = "^[\\w.-]+@([a-z0-9][a-z0-9-]*[a-z0-9]?\\.)+[a-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    /**
     * Fills the provided MimeMessage with necessary details for the reminder email.
     *
     * @param message          The MimeMessage object to be filled.
     * @param sendMail         The sender's email address.
     * @param reminderRequest  The reminder request data.
     * @param festival         The festival for which the reminder is being sent.
     * @param content          The HTML content of the email.
     * @param dateTime         The date and time at which the reminder email will be sent.
     * @return The filled MimeMessage object.
     */
    public MimeMessage fillMessage(MimeMessage message, String sendMail, Reminder.RequestDO reminderRequest, Festival festival, String content, LocalDateTime dateTime) {
        try {
            message.setFrom(new InternetAddress(sendMail, "FourReal", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(reminderRequest.getEmail()));

            message.setSubject("Upcoming Celebrations: " + festival.getName(), "UTF-8");
            message.setSubject("\uD83C\uDF89 Don't Miss Out! " + festival.getName() + " Reminder! \uD83C\uDF89\n", "UTF-8");
            message.setContent(content, "text/html;charset=UTF-8");

            message.setSentDate(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
            message.saveChanges();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return message;
    }

    /**
     * Builds HTML content for the reminder email using a template.
     *
     * @param festival         The festival for which the reminder email is being built.
     * @param reminderRequest  The reminder request data.
     * @return The HTML content for the reminder email.
     */
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

        String festivalName = festival.getName();
        String htmlText = builder.toString();
        String festivalDesc = String.format(emailBodyContent,
                festivalName, festival.getDate(), festivalName, festivalName, festivalName);
        htmlText = htmlText.replace("{festivalDesc}", festivalDesc);
        return htmlText;
    }
}
