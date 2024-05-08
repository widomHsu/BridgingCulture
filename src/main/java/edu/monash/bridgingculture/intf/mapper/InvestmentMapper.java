package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.investment.StockChangeInADay;
import edu.monash.bridgingculture.service.entity.investment.StockChangeInAYear;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InvestmentMapper {

    @Insert("<script> " +
            "INSERT INTO stock_top_year(name, symbol, priceLowest, priceHighest, PriceChange, changePercent) values" +
                "<foreach item='stock' collection='topStock' separator=',' index=''>" +
                    "(#{stock.name},#{stock.symbol},#{stock.priceLowest}," +
                    "#{stock.priceHighest},#{stock.PriceChange},#{stock.changePercent})" +
                "</foreach>" +
            "</script>"
    )
    int insertTopStock(List<StockChangeInAYear> topStock);

    @Update("TRUNCATE stock_top_year")
    int truncateTopStock();

    @Select("select * from stock_top_year LIMIT ${top}")
    List<StockChangeInAYear> getTopStock(int top);
    @Select("select count(*) from stock_top_year")
    int getNoOfRow();
}
