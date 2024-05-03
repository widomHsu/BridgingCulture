package edu.monash.bridgingculture.service.entity.investment;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MarketDTO {

    @SerializedName("^AXJO")
    private AXJODTO axjo;

    @NoArgsConstructor
    @Data
    public static class AXJODTO {
        @SerializedName("dataGranularity")
        private Integer dataGranularity;
        @SerializedName("symbol")
        private String symbol;
        @SerializedName("timestamp")
        private List<Integer> timestamp;
        @SerializedName("end")
        private Integer end;
        @SerializedName("previousClose")
        private Double previousClose;
        @SerializedName("chartPreviousClose")
        private Double chartPreviousClose;
        @SerializedName("start")
        private Integer start;
        @SerializedName("close")
        private List<Double> close;
    }
}
