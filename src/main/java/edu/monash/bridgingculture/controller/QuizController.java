package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.intf.QuizService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Resource
    QuizService quizService;

    @GetMapping("/getQuiz")
    public ResponseDO getQuiz(){
        return quizService.getQuiz();
    }
    @PostMapping("/submitQuiz")
    public ResponseDO submitQuiz(@RequestBody List<String> options){
        quizService.submitQuiz(options);
        return null;
    }
}
