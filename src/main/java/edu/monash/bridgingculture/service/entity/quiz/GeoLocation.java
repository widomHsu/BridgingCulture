package edu.monash.bridgingculture.service.entity.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GeoLocation {

    @JsonProperty("type")
    private String type;
    @JsonProperty("query")
    private List<String> query;
    @JsonProperty("features")
    private List<FeaturesDTO> features;
    @JsonProperty("attribution")
    private String attribution;

    @NoArgsConstructor
    @Data
    public static class FeaturesDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("type")
        private String type;
        @JsonProperty("place_type")
        private List<String> placeType;
        @JsonProperty("relevance")
        private Integer relevance;
        @JsonProperty("properties")
        private PropertiesDTO properties;
        @JsonProperty("text")
        private String text;
        @JsonProperty("place_name")
        private String placeName;
        @JsonProperty("bbox")
        private List<Double> bbox;
        @JsonProperty("center")
        private List<Double> center;
        @JsonProperty("geometry")
        private GeometryDTO geometry;
        @JsonProperty("context")
        private List<ContextDTO> context;

        @NoArgsConstructor
        @Data
        public static class PropertiesDTO {
            @JsonProperty("mapbox_id")
            private String mapboxId;
            @JsonProperty("wikidata")
            private String wikidata;
        }

        @NoArgsConstructor
        @Data
        public static class GeometryDTO {
            @JsonProperty("type")
            private String type;
            @JsonProperty("coordinates")
            private List<Double> coordinates;
        }

        @NoArgsConstructor
        @Data
        public static class ContextDTO {
            @JsonProperty("id")
            private String id;
            @JsonProperty("mapbox_id")
            private String mapboxId;
            @JsonProperty("wikidata")
            private String wikidata;
            @JsonProperty("text")
            private String text;
            @JsonProperty("short_code")
            private String shortCode;
        }
    }
}
