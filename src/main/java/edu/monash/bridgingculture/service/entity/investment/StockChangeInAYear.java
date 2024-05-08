package edu.monash.bridgingculture.service.entity.investment;

import lombok.Data;

@Data
public class StockChangeInAYear {
    String name;
    String symbol;
    String priceLowest;
    String priceHighest;
    String PriceChange;
    String changePercent;
}
