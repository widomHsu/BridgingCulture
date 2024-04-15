package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.QuizService;
import edu.monash.bridgingculture.intf.mapper.QuizMapper;
import edu.monash.bridgingculture.service.entity.quiz.*;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import edu.monash.bridgingculture.service.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the QuizService interface providing quiz-related functionality.
 */
@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    @Resource
    QuizMapper quizMapper;
    @Resource
    RandomUtil randomUtil;
    @Resource
    HttpUtil httpUtil;

    /**
     * Retrieves a list of randomized quiz questions.
     *
     * @return List of Question representing the randomized quiz questions
     */
    @Override
    public List<Question> getQuiz() {
        List<Question> questionList = quizMapper.getQuestionnaire();
        return randomUtil.getRandomElement(questionList, 4, 6, 0.25);
    }

    /**
     * Submits a quiz with provided options and retrieves information about the culture and attractions.
     *
     * @param options List of strings representing options chosen for the quiz
     * @return TripAdvisorDO containing information about the culture and attractions
     */
    @Override
    public TripAdvisorDO submitQuiz(List<String> options) {
        // 1. get the possible favourite country
        Set<String> set = new HashSet<>(options);
        List<Tag> tagList = quizMapper.getTags();
        Map<String, Integer> map = new HashMap<>(); // <country, marks>
        for(Tag tag: tagList){
            String country = tag.getCountry();
            String[] tags = tag.getTags().split("&");
            for(String s: tags){
                if(set.contains(s)){
                    map.put(country, map.getOrDefault(country, 0) + 1);
                }
            }
        }
        log.info(map.entrySet().toString());
        List<Map.Entry<String, Integer>> sortedList = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        String ethnic = sortedList.get(sortedList.size()-1).getKey();
        // 2. find the suburb with the most people of that background living there
        Census census = quizMapper.getCensusByEthnic(ethnic);
        census.setEthnic(Character.toUpperCase(census.getEthnic().charAt(0)) + census.getEthnic().substring(1));
        String suburb = census.getSuburb();
        log.info(suburb);

        // 3. find attractions about that culture in that suburb
        TripAdvisor tripAdvisor = httpUtil.getTripAdvisor(suburb, ethnic);
        return new TripAdvisorDO(census, tripAdvisor);
    }
}
