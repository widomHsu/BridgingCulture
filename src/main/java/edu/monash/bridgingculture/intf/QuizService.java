package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.quiz.Question;
import edu.monash.bridgingculture.service.entity.quiz.TripAdvisorDO;

import java.util.List;

public interface QuizService {
    List<Question> getQuiz();
    TripAdvisorDO submitQuiz(List<String> options);
}
