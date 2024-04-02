package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.quiz.Census;
import edu.monash.bridgingculture.service.entity.quiz.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuizMapper {

//    @Select("select * from ")
    Question getQuiz();

    @Select("select * from top5_suburbs_vic where ethnic = #{ethnic} limit 1;")
    Census getCensusByEthnic(String ethnic);
}
