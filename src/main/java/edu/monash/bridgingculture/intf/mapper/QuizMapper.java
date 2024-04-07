package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.quiz.Census;
import edu.monash.bridgingculture.service.entity.quiz.Question;
import edu.monash.bridgingculture.service.entity.quiz.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuizMapper {


    @Select("select * from top5_suburbs_vic where ethnic = #{ethnic} limit 1;")
    Census getCensusByEthnic(String ethnic);

    @Select("select question_content, options from questionnaire;")
    List<Question> getQuestionnaire();

    @Select("select * from tag;")
    List<Tag> getTags();
}
