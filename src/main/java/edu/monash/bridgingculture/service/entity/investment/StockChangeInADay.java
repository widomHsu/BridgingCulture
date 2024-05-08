package edu.monash.bridgingculture.service.entity.investment;

import lombok.Data;

@Data
public class StockChangeInADay {

    String name;
    String symbol;
    String pricePreviousClose;
    String priceNow;
    String PriceChange;
    String changePercent;
}
