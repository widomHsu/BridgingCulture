package edu.monash.bridgingculture.intf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface APIMapper {

    @Select("select value from api_ley where name = 'X-RapidAPI-Key'")
    String getXRapidAPIKey();
}
