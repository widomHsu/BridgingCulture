package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.QuizService;
import edu.monash.bridgingculture.intf.mapper.QuizMapper;
import edu.monash.bridgingculture.service.entity.quiz.Census;
import edu.monash.bridgingculture.service.entity.quiz.Question;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.quiz.Tag;
import edu.monash.bridgingculture.service.entity.quiz.TripAdvisor;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import edu.monash.bridgingculture.service.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    @Resource
    QuizMapper quizMapper;
    @Resource
    RandomUtil randomUtil;
    @Resource
    HttpUtil httpUtil;

    @Override
    public ResponseDO getQuiz() {
        // todo
        List<Question> questionList = null; // from database, the size must greater than 10

        return ResponseDO.success(randomUtil.getRandomElement(questionList, 5, 10, 0.5));
    }

    @Override
    public ResponseDO submitQuiz(List<String> options) {
        // 1. get the possible favourite country
        Set<String> set = new HashSet<>(options);
        List<Tag> tagList = null; // todo
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
        List<Map.Entry<String, Integer>> sortedList = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        String preferCountry = sortedList.get(sortedList.size()-1).getKey();

        // 2. find the suburb with the most people of that background living there
        String ethnic = countryToEthnic(preferCountry);
        Census census = quizMapper.getCensusByEthnic(ethnic);
        String suburb = census.getSuburb();
        log.info(suburb);

        // 3. find attractions about that culture in that suburb
        TripAdvisor tripAdvisor = httpUtil.getTripAdvisor(suburb, preferCountry);
        return ResponseDO.success(tripAdvisor);
    }

    public static String countryToEthnic(String country){
        HashMap<String, String> map = new HashMap<>();
        map.put("china", "chinese");
        map.put("india", "indian");
        map.put("england", "english");
        map.put("new zealand", "new zealander");
        map.put("philippines", "filipino");
        return map.get(country);
    }
}
