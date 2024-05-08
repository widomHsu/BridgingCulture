package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.investment.MarketSummary;
import edu.monash.bridgingculture.service.entity.investment.StockChangeInADay;
import edu.monash.bridgingculture.service.entity.investment.StockChangeInAYear;

import java.util.List;

public interface InvestmentService {
    ResponseDO getStockAndMarket(String name, String interval, String range);
    MarketSummary getMarketPrice();
    List<StockChangeInADay> getTopStockByDay(int top);
    List<StockChangeInAYear> getTopStockByYear(int top);
    void updateTopStockByYear();
}
