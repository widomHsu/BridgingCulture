package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.controller.utils.ReminderUtils;
import edu.monash.bridgingculture.intf.FestivalService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.ChatBot;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

@RestController
@RequestMapping("/festival")
@Slf4j
public class FestivalsController {

    @Resource
    FestivalService festivalService;
    @Resource
    ReminderUtils reminderUtils;

    /**
     * Handles HTTP POST requests to "/chatBot".
     * This method logs the incoming request and processes the chatBot query.
     *
     * @param request  The HTTPServletRequest object representing the incoming request.
     * @param response The HttpServletResponse object representing the outgoing response.
     * @param chatBot  The ChatBot object containing the query.
     * @return A ResponseDO object containing the result of the chatBot processing.
     */
    @PostMapping("/chatBot")
    @Log
    public ResponseDO chatBot(HttpServletRequest request, HttpServletResponse response, @RequestBody ChatBot chatBot){
        if(chatBot == null || StringUtils.isEmpty(chatBot.getQuery()))
            return ResponseDO.fail("The query content is null.");
        int allowed = festivalService.isAllowed(request.getRemoteAddr());
        if(allowed != 0)
            return ResponseDO.fail(String.format("You can ask up to 5 questions per minute, please retry after %d seconds.", allowed));
        return ResponseDO.success(festivalService.chatBot(request, response, chatBot.getQuery()));
    }

    /**
     * Handles HTTP POST requests to the root ("/") endpoint.
     * This method logs the incoming request and retrieves festival data based on the provided parameters.
     *
     * @param festivalDO The FestivalDO object containing the parameters for retrieving festival data.
     * @return A ResponseDO object containing the retrieved festival data or an error message.
     * @throws InterruptedException If the thread is interrupted while sleeping.
     */
    @PostMapping("")
    @Log
    public ResponseDO getFestival(@RequestBody Festival.FestivalDO festivalDO) throws InterruptedException {
        if(CollectionUtils.isEmpty(festivalDO.getCountries()))
            return ResponseDO.fail("The country is null.");

        ResponseDO responseDO = checkYear(festivalDO.getYear());
        if(!responseDO.isSuccess())
            return responseDO;
        int year = (int) responseDO.getData();

        int month = -1;
        if(festivalDO.getMonth() != null){
            month = getNumber(festivalDO.getMonth());
            if(month == -1)
                return ResponseDO.fail("Invalid month.");
            if(month < 1 || month > 12)
                return ResponseDO.fail("The month range from 1 to 12 inclusive.");
        }

        return ResponseDO.success(
                festivalService.getFestivals(festivalDO.getCountries(),
                year,
                month,
                festivalDO.getTypes())
        );
    }

    /**
     * Handles HTTP POST requests to the "/reminder" endpoint.
     * This method logs the incoming request and creates a reminder based on the provided data.
     *
     * @param reminderRequest The RequestDO object containing the reminder request data.
     * @return A ResponseDO object containing the result of the reminder creation operation.
     */
    @PostMapping("/reminder")
    @Log
    public ResponseDO createReminder(@RequestBody Reminder.RequestDO reminderRequest) {
        if(!reminderUtils.checkEmail(reminderRequest.getEmail()))
            return ResponseDO.fail("Invalid Email.");
        return festivalService.createReminder(reminderRequest);
    }

    @PostMapping("/addFestivals")
    @Log
    public ResponseDO addFestivals(@RequestParam("country") List<String> countries,
                                   @RequestParam("year") String year,
                                   @RequestParam("month") String month){
        return festivalService.addFestivals(countries, Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * Checks the validity of the provided year string.
     *
     * @param yearStr The string representation of the year to be checked.
     * @return A ResponseDO object indicating the result of the year validation.
     */
    public static ResponseDO checkYear(String yearStr){
        if(StringUtils.isEmpty(yearStr))
            return ResponseDO.fail("The year is null.");

        int year = getNumber(yearStr);
        if(year == -1)
            return ResponseDO.fail("Invalid year.");
        if(year < 2024 || year > 2030)
            return ResponseDO.fail("The year range from 2024 to 2030 inclusive.");
        return ResponseDO.success(year);
    }

    /**
     * Converts a string to an integer.
     *
     * @param str The string to be converted.
     * @return The integer representation of the string, or -1 if conversion fails.
     */
    public static int getNumber(String str){
        if(str == null)
            return -1;
        try{
            return Integer.parseInt(str);
        }catch (Exception e){
            return -1;
        }
    }
}