package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.ResponseDO;

import java.util.List;

public interface QuizService {
    ResponseDO getQuiz();
    ResponseDO submitQuiz(List<String> options);
}
