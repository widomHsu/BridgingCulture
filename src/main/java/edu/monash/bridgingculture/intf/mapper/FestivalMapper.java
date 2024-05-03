package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.festival.ChatBotHistory;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FestivalMapper {

    @Insert("INSERT INTO festival_api (country, iso, year, date, day, name, type) " +
            "VALUES (#{country}, #{iso}, #{year}, #{date}, #{day}, #{name}, #{type});")
    int addFestival(Festival festival);

//    @Insert("INSERT INTO festival_fitter (country, iso, year, date, day, name, type) " +
//            "VALUES (#{country}, #{iso}, #{year}, #{date}, #{day}, #{name}, #{type});")
//    int addFestival(Festival festival);

    @Select(
            "<script> " +
                "SELECT * FROM festival_api WHERE country in" +
                "<foreach item='country' collection='countries' separator=',' open='(' close=')' index=''>" +
                    "#{country}" +
                "</foreach>" +
                "AND year = #{year} " +
            "</script>"
    )
    List<Festival> getFestival(@Param("countries") List<String> countries, int year);
    
    @Select("select * from festival_api where id = #{id};")
    Festival getFestivalById(int id);

    @Insert("insert into chatbot_history(ip, role, content, time) " +
            "values (#{ip},#{role},#{content},#{time});")
    int addHistory(ChatBotHistory history);

    @Select("select * from chatbot_history where ip = #{ip} order by time DESC Limit 10;")
    List<ChatBotHistory> getHistory(String ip);

    @Delete("delete from chatbot_history where time < #{time}")
    int removeHistory(long time);
}
