package edu.monash.bridgingculture.service.entity.investment;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YahooScreenerDTO {

    @SerializedName("finance")
    private FinanceDTO finance;

    @NoArgsConstructor
    @Data
    public static class FinanceDTO {
        @SerializedName("result")
        private List<ResultDTO> result;
        @SerializedName("error")
        private Object error;

        @NoArgsConstructor
        @Data
        public static class ResultDTO {
            @SerializedName("start")
            private Integer start;
            @SerializedName("count")
            private Integer count;
            @SerializedName("total")
            private Integer total;
            @SerializedName("quotes")
            private List<QuotesDTO> quotes;
            @SerializedName("useRecords")
            private Boolean useRecords;

            @NoArgsConstructor
            @Data
            public static class QuotesDTO {
                @SerializedName("language")
                private String language;
                @SerializedName("region")
                private String region;
                @SerializedName("quoteType")
                private String quoteType;
                @SerializedName("typeDisp")
                private String typeDisp;
                @SerializedName("quoteSourceName")
                private String quoteSourceName;
                @SerializedName("triggerable")
                private Boolean triggerable;
                @SerializedName("customPriceAlertConfidence")
                private String customPriceAlertConfidence;
                @SerializedName("currency")
                private String currency;
                @SerializedName("regularMarketChangePercent")
                private Double regularMarketChangePercent;
                @SerializedName("exchange")
                private String exchange;
                @SerializedName("fiftyTwoWeekLow")
                private Double fiftyTwoWeekLow;
                @SerializedName("fiftyTwoWeekHigh")
                private Double fiftyTwoWeekHigh;
                @SerializedName("shortName")
                private String shortName;
                @SerializedName("hasPrePostMarketData")
                private Boolean hasPrePostMarketData;
                @SerializedName("firstTradeDateMilliseconds")
                private Long firstTradeDateMilliseconds;
                @SerializedName("priceHint")
                private Integer priceHint;
                @SerializedName("regularMarketChange")
                private Double regularMarketChange;
                @SerializedName("regularMarketTime")
                private Integer regularMarketTime;
                @SerializedName("regularMarketPrice")
                private Double regularMarketPrice;
                @SerializedName("regularMarketDayHigh")
                private Double regularMarketDayHigh;
                @SerializedName("regularMarketDayRange")
                private String regularMarketDayRange;
                @SerializedName("regularMarketDayLow")
                private Double regularMarketDayLow;
                @SerializedName("regularMarketVolume")
                private Integer regularMarketVolume;
                @SerializedName("regularMarketPreviousClose")
                private Double regularMarketPreviousClose;
                @SerializedName("bid")
                private Double bid;
                @SerializedName("ask")
                private Double ask;
                @SerializedName("market")
                private String market;
                @SerializedName("messageBoardId")
                private String messageBoardId;
                @SerializedName("fullExchangeName")
                private String fullExchangeName;
                @SerializedName("financialCurrency")
                private String financialCurrency;
                @SerializedName("regularMarketOpen")
                private Double regularMarketOpen;
                @SerializedName("averageDailyVolume3Month")
                private Integer averageDailyVolume3Month;
                @SerializedName("averageDailyVolume10Day")
                private Integer averageDailyVolume10Day;
                @SerializedName("fiftyTwoWeekLowChange")
                private Double fiftyTwoWeekLowChange;
                @SerializedName("fiftyTwoWeekLowChangePercent")
                private Double fiftyTwoWeekLowChangePercent;
                @SerializedName("fiftyTwoWeekRange")
                private String fiftyTwoWeekRange;
                @SerializedName("fiftyTwoWeekHighChange")
                private Double fiftyTwoWeekHighChange;
                @SerializedName("fiftyTwoWeekHighChangePercent")
                private Double fiftyTwoWeekHighChangePercent;
                @SerializedName("fiftyTwoWeekChangePercent")
                private Double fiftyTwoWeekChangePercent;
                @SerializedName("marketState")
                private String marketState;
                @SerializedName("sharesOutstanding")
                private Long sharesOutstanding;
                @SerializedName("fiftyDayAverage")
                private Double fiftyDayAverage;
                @SerializedName("fiftyDayAverageChange")
                private Double fiftyDayAverageChange;
                @SerializedName("fiftyDayAverageChangePercent")
                private Double fiftyDayAverageChangePercent;
                @SerializedName("twoHundredDayAverage")
                private Double twoHundredDayAverage;
                @SerializedName("twoHundredDayAverageChange")
                private Double twoHundredDayAverageChange;
                @SerializedName("twoHundredDayAverageChangePercent")
                private Double twoHundredDayAverageChangePercent;
                @SerializedName("marketCap")
                private Long marketCap;
                @SerializedName("sourceInterval")
                private Integer sourceInterval;
                @SerializedName("exchangeDataDelayedBy")
                private Integer exchangeDataDelayedBy;
                @SerializedName("exchangeTimezoneName")
                private String exchangeTimezoneName;
                @SerializedName("exchangeTimezoneShortName")
                private String exchangeTimezoneShortName;
                @SerializedName("gmtOffSetMilliseconds")
                private Integer gmtOffSetMilliseconds;
                @SerializedName("prevName")
                private String prevName;
                @SerializedName("nameChangeDate")
                private String nameChangeDate;
                @SerializedName("esgPopulated")
                private Boolean esgPopulated;
                @SerializedName("tradeable")
                private Boolean tradeable;
                @SerializedName("cryptoTradeable")
                private Boolean cryptoTradeable;
                @SerializedName("symbol")
                private String symbol;
                @SerializedName("averageAnalystRating")
                private String averageAnalystRating;
                @SerializedName("bidSize")
                private Integer bidSize;
                @SerializedName("askSize")
                private Integer askSize;
                @SerializedName("longName")
                private String longName;
                @SerializedName("earningsTimestamp")
                private Integer earningsTimestamp;
                @SerializedName("earningsTimestampStart")
                private Integer earningsTimestampStart;
                @SerializedName("earningsTimestampEnd")
                private Integer earningsTimestampEnd;
                @SerializedName("trailingAnnualDividendRate")
                private Double trailingAnnualDividendRate;
                @SerializedName("dividendRate")
                private Double dividendRate;
                @SerializedName("trailingAnnualDividendYield")
                private Double trailingAnnualDividendYield;
                @SerializedName("dividendYield")
                private Double dividendYield;
                @SerializedName("epsTrailingTwelveMonths")
                private Double epsTrailingTwelveMonths;
                @SerializedName("epsForward")
                private Double epsForward;
                @SerializedName("epsCurrentYear")
                private Double epsCurrentYear;
                @SerializedName("priceEpsCurrentYear")
                private Double priceEpsCurrentYear;
                @SerializedName("bookValue")
                private Double bookValue;
                @SerializedName("forwardPE")
                private Double forwardPE;
                @SerializedName("priceToBook")
                private Double priceToBook;
                @SerializedName("trailingPE")
                private Double trailingPE;
                @SerializedName("ipoExpectedDate")
                private String ipoExpectedDate;
            }
        }
    }
}
