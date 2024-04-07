package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.intf.QuizService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class represents a controller for managing quiz-related operations.
 */
@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Resource
    QuizService quizService;

    /**
     * Retrieves a quiz.
     *
     * @return ResponseDO containing the success status and retrieved quiz
     */
    @GetMapping("")
    @Log
    public ResponseDO getQuiz(){
        return ResponseDO.success(quizService.getQuiz());
    }

    /**
     * Submits a quiz with provided options.
     *
     * @param options List of strings representing options chosen for the quiz
     * @return ResponseDO containing the success status and result of the quiz submission
     */
    @PostMapping("")
    @Log
    public ResponseDO submitQuiz(@RequestBody List<String> options){
        if(CollectionUtils.isEmpty(options))
            return ResponseDO.fail("The list of option is null.");
        return ResponseDO.success(quizService.submitQuiz(options));
    }
}
