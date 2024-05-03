package edu.monash.bridgingculture.service.entity.investment;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TopStockDTO {

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
                @SerializedName("symbol")
                private String symbol;
                @SerializedName("twoHundredDayAverageChangePercent")
                private TwoHundredDayAverageChangePercentDTO twoHundredDayAverageChangePercent;
                @SerializedName("fiftyTwoWeekLowChangePercent")
                private FiftyTwoWeekLowChangePercentDTO fiftyTwoWeekLowChangePercent;
                @SerializedName("language")
                private String language;
                @SerializedName("regularMarketDayRange")
                private RegularMarketDayRangeDTO regularMarketDayRange;
                @SerializedName("earningsTimestampEnd")
                private EarningsTimestampEndDTO earningsTimestampEnd;
                @SerializedName("regularMarketDayHigh")
                private RegularMarketDayHighDTO regularMarketDayHigh;
                @SerializedName("twoHundredDayAverageChange")
                private TwoHundredDayAverageChangeDTO twoHundredDayAverageChange;
                @SerializedName("twoHundredDayAverage")
                private TwoHundredDayAverageDTO twoHundredDayAverage;
                @SerializedName("askSize")
                private AskSizeDTO askSize;
                @SerializedName("bookValue")
                private BookValueDTO bookValue;
                @SerializedName("marketCap")
                private MarketCapDTO marketCap;
                @SerializedName("fiftyTwoWeekHighChange")
                private FiftyTwoWeekHighChangeDTO fiftyTwoWeekHighChange;
                @SerializedName("fiftyTwoWeekRange")
                private FiftyTwoWeekRangeDTO fiftyTwoWeekRange;
                @SerializedName("fiftyDayAverageChange")
                private FiftyDayAverageChangeDTO fiftyDayAverageChange;
                @SerializedName("averageDailyVolume3Month")
                private AverageDailyVolume3MonthDTO averageDailyVolume3Month;
                @SerializedName("firstTradeDateMilliseconds")
                private Long firstTradeDateMilliseconds;
                @SerializedName("exchangeDataDelayedBy")
                private Integer exchangeDataDelayedBy;
                @SerializedName("fiftyTwoWeekChangePercent")
                private FiftyTwoWeekChangePercentDTO fiftyTwoWeekChangePercent;
                @SerializedName("trailingAnnualDividendRate")
                private TrailingAnnualDividendRateDTO trailingAnnualDividendRate;
                @SerializedName("hasPrePostMarketData")
                private Boolean hasPrePostMarketData;
                @SerializedName("fiftyTwoWeekLow")
                private FiftyTwoWeekLowDTO fiftyTwoWeekLow;
                @SerializedName("market")
                private String market;
                @SerializedName("regularMarketVolume")
                private RegularMarketVolumeDTO regularMarketVolume;
                @SerializedName("quoteSourceName")
                private String quoteSourceName;
                @SerializedName("messageBoardId")
                private String messageBoardId;
                @SerializedName("priceHint")
                private Integer priceHint;
                @SerializedName("exchange")
                private String exchange;
                @SerializedName("regularMarketDayLow")
                private RegularMarketDayLowDTO regularMarketDayLow;
                @SerializedName("sourceInterval")
                private Integer sourceInterval;
                @SerializedName("shortName")
                private String shortName;
                @SerializedName("region")
                private String region;
                @SerializedName("fiftyDayAverageChangePercent")
                private FiftyDayAverageChangePercentDTO fiftyDayAverageChangePercent;
                @SerializedName("fullExchangeName")
                private String fullExchangeName;
                @SerializedName("earningsTimestampStart")
                private EarningsTimestampStartDTO earningsTimestampStart;
                @SerializedName("financialCurrency")
                private String financialCurrency;
                @SerializedName("gmtOffSetMilliseconds")
                private Integer gmtOffSetMilliseconds;
                @SerializedName("regularMarketOpen")
                private RegularMarketOpenDTO regularMarketOpen;
                @SerializedName("regularMarketTime")
                private RegularMarketTimeDTO regularMarketTime;
                @SerializedName("regularMarketChangePercent")
                private RegularMarketChangePercentDTO regularMarketChangePercent;
                @SerializedName("trailingAnnualDividendYield")
                private TrailingAnnualDividendYieldDTO trailingAnnualDividendYield;
                @SerializedName("quoteType")
                private String quoteType;
                @SerializedName("averageDailyVolume10Day")
                private AverageDailyVolume10DayDTO averageDailyVolume10Day;
                @SerializedName("fiftyTwoWeekLowChange")
                private FiftyTwoWeekLowChangeDTO fiftyTwoWeekLowChange;
                @SerializedName("fiftyTwoWeekHighChangePercent")
                private FiftyTwoWeekHighChangePercentDTO fiftyTwoWeekHighChangePercent;
                @SerializedName("typeDisp")
                private String typeDisp;
                @SerializedName("tradeable")
                private Boolean tradeable;
                @SerializedName("currency")
                private String currency;
                @SerializedName("sharesOutstanding")
                private SharesOutstandingDTO sharesOutstanding;
                @SerializedName("regularMarketPreviousClose")
                private RegularMarketPreviousCloseDTO regularMarketPreviousClose;
                @SerializedName("fiftyTwoWeekHigh")
                private FiftyTwoWeekHighDTO fiftyTwoWeekHigh;
                @SerializedName("exchangeTimezoneName")
                private String exchangeTimezoneName;
                @SerializedName("bidSize")
                private BidSizeDTO bidSize;
                @SerializedName("regularMarketChange")
                private RegularMarketChangeDTO regularMarketChange;
                @SerializedName("cryptoTradeable")
                private Boolean cryptoTradeable;
                @SerializedName("fiftyDayAverage")
                private FiftyDayAverageDTO fiftyDayAverage;
                @SerializedName("exchangeTimezoneShortName")
                private String exchangeTimezoneShortName;
                @SerializedName("marketState")
                private String marketState;
                @SerializedName("customPriceAlertConfidence")
                private String customPriceAlertConfidence;
                @SerializedName("regularMarketPrice")
                private RegularMarketPriceDTO regularMarketPrice;
                @SerializedName("earningsTimestamp")
                private EarningsTimestampDTO earningsTimestamp;
                @SerializedName("ask")
                private AskDTO ask;
                @SerializedName("epsTrailingTwelveMonths")
                private EpsTrailingTwelveMonthsDTO epsTrailingTwelveMonths;
                @SerializedName("bid")
                private BidDTO bid;
                @SerializedName("priceToBook")
                private PriceToBookDTO priceToBook;
                @SerializedName("triggerable")
                private Boolean triggerable;
                @SerializedName("longName")
                private String longName;
                @SerializedName("prevName")
                private String prevName;
                @SerializedName("nameChangeDate")
                private String nameChangeDate;

                @NoArgsConstructor
                @Data
                public static class TwoHundredDayAverageChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekLowChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketDayRangeDTO {
                    @SerializedName("raw")
                    private String raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class EarningsTimestampEndDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketDayHighDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class TwoHundredDayAverageChangeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class TwoHundredDayAverageDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class AskSizeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class BookValueDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class MarketCapDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekHighChangeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekRangeDTO {
                    @SerializedName("raw")
                    private String raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyDayAverageChangeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class AverageDailyVolume3MonthDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class TrailingAnnualDividendRateDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekLowDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketVolumeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketDayLowDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyDayAverageChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class EarningsTimestampStartDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketOpenDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketTimeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class TrailingAnnualDividendYieldDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class AverageDailyVolume10DayDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekLowChangeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekHighChangePercentDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class SharesOutstandingDTO {
                    @SerializedName("raw")
                    private Long raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketPreviousCloseDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyTwoWeekHighDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class BidSizeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketChangeDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class FiftyDayAverageDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class RegularMarketPriceDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class EarningsTimestampDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                    @SerializedName("longFmt")
                    private String longFmt;
                }

                @NoArgsConstructor
                @Data
                public static class AskDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class EpsTrailingTwelveMonthsDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class BidDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }

                @NoArgsConstructor
                @Data
                public static class PriceToBookDTO {
                    @SerializedName("raw")
                    private Double raw;
                    @SerializedName("fmt")
                    private String fmt;
                }
            }
        }
    }
}
