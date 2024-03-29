package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper {

    @Select("select * from items;")
    List<Item> getItems();
}
