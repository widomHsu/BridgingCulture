package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.controller.utils.ReminderUtils;
import edu.monash.bridgingculture.intf.FestivalService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.festival.ChatBot;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.festival.Reminder;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("")
    @Log
    public ResponseDO getFestival(@RequestParam("country") String country,
                                  @RequestParam("year") String year,
                                  @Nullable @RequestParam("month") String month,
                                  @Nullable @RequestParam("type") String type){
        if(StringUtils.isEmpty(country))
            return ResponseDO.fail("The country is null.");
        if(StringUtils.isEmpty(year))
            return ResponseDO.fail("The year is null.");
        List<Festival> festival =
                festivalService.getFestival(country,
                        Integer.parseInt(year),
                        month==null? 0:Integer.parseInt(month),
                        type);
        return ResponseDO.success(festival);
    }

    @PostMapping("")
    @Log
    public ResponseDO createReminder(@RequestBody Reminder.RequestDO reminderRequest) throws Exception {
        if(!reminderUtils.checkEmail(reminderRequest.getEmail()))
            return ResponseDO.fail("Invalid Email.");
        return festivalService.createReminder(reminderRequest);
    }
}