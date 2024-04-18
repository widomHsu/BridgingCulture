package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.festival.Festival;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FestivalMapper {

    @Insert("INSERT INTO festival_api (country, iso, year, date, day, name, type) " +
            "VALUES (#{country}, #{iso}, #{year}, #{date}, #{day}, #{name}, #{type});")
    int addFestival(Festival festival);

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
}
