package edu.monash.bridgingculture.service.entity.investment;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class StockDTO {

    @SerializedName("chart")
    private ChartDTO chart;

    @NoArgsConstructor
    @Data
    public static class ChartDTO {
        @SerializedName("result")
        private List<ResultDTO> result;
        @SerializedName("error")
        private Object error;

        @NoArgsConstructor
        @Data
        public static class ResultDTO {
            @SerializedName("meta")
            private MetaDTO meta;
            @SerializedName("timestamp")
            private List<Integer> timestamp;
            @SerializedName("indicators")
            private IndicatorsDTO indicators;

            @NoArgsConstructor
            @Data
            public static class MetaDTO {
                @SerializedName("currency")
                private String currency;
                @SerializedName("symbol")
                private String symbol;
                @SerializedName("exchangeName")
                private String exchangeName;
                @SerializedName("fullExchangeName")
                private String fullExchangeName;
                @SerializedName("instrumentType")
                private String instrumentType;
                @SerializedName("firstTradeDate")
                private Integer firstTradeDate;
                @SerializedName("regularMarketTime")
                private Integer regularMarketTime;
                @SerializedName("hasPrePostMarketData")
                private Boolean hasPrePostMarketData;
                @SerializedName("gmtoffset")
                private Integer gmtoffset;
                @SerializedName("timezone")
                private String timezone;
                @SerializedName("exchangeTimezoneName")
                private String exchangeTimezoneName;
                @SerializedName("regularMarketPrice")
                private Double regularMarketPrice;
                @SerializedName("fiftyTwoWeekHigh")
                private Double fiftyTwoWeekHigh;
                @SerializedName("fiftyTwoWeekLow")
                private Double fiftyTwoWeekLow;
                @SerializedName("regularMarketDayHigh")
                private Double regularMarketDayHigh;
                @SerializedName("regularMarketDayLow")
                private Double regularMarketDayLow;
                @SerializedName("regularMarketVolume")
                private Integer regularMarketVolume;
                @SerializedName("chartPreviousClose")
                private Double chartPreviousClose;
                @SerializedName("previousClose")
                private Double previousClose;
                @SerializedName("scale")
                private Integer scale;
                @SerializedName("priceHint")
                private Integer priceHint;
                @SerializedName("currentTradingPeriod")
                private CurrentTradingPeriodDTO currentTradingPeriod;
                @SerializedName("tradingPeriods")
                private TradingPeriodsDTO tradingPeriods;
                @SerializedName("dataGranularity")
                private String dataGranularity;
                @SerializedName("range")
                private String range;
                @SerializedName("validRanges")
                private List<String> validRanges;

                @NoArgsConstructor
                @Data
                public static class CurrentTradingPeriodDTO {
                    @SerializedName("pre")
                    private PreDTO pre;
                    @SerializedName("regular")
                    private RegularDTO regular;
                    @SerializedName("post")
                    private PostDTO post;

                    @NoArgsConstructor
                    @Data
                    public static class PreDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class RegularDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class PostDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class TradingPeriodsDTO {
                    @SerializedName("pre")
                    private List<List<PreDTO>> pre;
                    @SerializedName("post")
                    private List<List<PostDTO>> post;
                    @SerializedName("regular")
                    private List<List<RegularDTO>> regular;

                    @NoArgsConstructor
                    @Data
                    public static class PreDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class PostDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class RegularDTO {
                        @SerializedName("timezone")
                        private String timezone;
                        @SerializedName("start")
                        private Integer start;
                        @SerializedName("end")
                        private Integer end;
                        @SerializedName("gmtoffset")
                        private Integer gmtoffset;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class IndicatorsDTO {
                @SerializedName("quote")
                private List<QuoteDTO> quote;

                @NoArgsConstructor
                @Data
                public static class QuoteDTO {
                    @SerializedName("low")
                    private List<Double> low;
                    @SerializedName("open")
                    private List<Double> open;
                    @SerializedName("high")
                    private List<Double> high;
                    @SerializedName("close")
                    private List<Double> close;
                    @SerializedName("volume")
                    private List<Integer> volume;
                }
            }
        }
    }
}
