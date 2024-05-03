package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.investment.StockChange;

import java.util.List;

public interface InvestmentService {
    ResponseDO getStockAndMarket(String name, String interval, String range);
    List<StockChange> getTopStock(int top);
}
