package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.resource.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResourceMapper {
    @Select("select * from cultural_info where country = #{country};")
    CulturalInfo getCulturalInfo(String country);

    @Select("select group_, sub_group, population_number, population_percentage from demographic_info where country = #{country};")
    @Result(property = "group", column = "group_")
    List<DemographicInfo> getDemographicInfo(String country);

    @Select("select festival, significance, image_url from festival_info where country = #{country};")
    List<FestivalInfo> getFestivalInfo(String country);

    @Select("select language from language_info where country = #{country};")
    List<String> getLanguageInfo(String country);

    @Select("select image_url from cuisine_images where country = #{country};")
    List<CuisineImages> getCuisineImages(String country);

    @Select("select can,cannot from cultural_etiquette where country = #{country};")
    List<CulturalEtiquette> getCulturalEtiquette(String country);
}
