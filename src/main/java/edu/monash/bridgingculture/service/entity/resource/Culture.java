package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;
import java.util.List;

@Data
public class Culture {
    private String country;
    private String about;
    private List<DemographicInfo> demographicInfos;
    private String traditionalClothing;
    private List<Greeting> greetings;
    private String cuisinesInfo;
    private List<String> cuisineImages;
    private String caveatInfo;
    private List<CulturalEtiquette> culturalEtiquettes;
    private List<FestivalInfo> festivalInfos;
    private List<String> languageInfos;
    private List<Reference> ReferenceInfos;

    @Data
    public static class CulturalInfo {
        private String country;
        private String about;
        private String traditionalClothing;
        private String cuisinesInfo;
        private String caveatInfo;
    }
    @Data
    public static class DemographicInfo {
        private String group;
        private String subGroup;
        private Integer populationNumber;
        private Double populationPercentage;
    }
    @Data
    public static class CulturalEtiquette {
        private String can;
        private String cannot;
    }
    @Data
    public static class FestivalInfo {
        private String festival;
        private String date;
        private String significance;
        private String imageUrl;
    }
    @Data
    public static class Greeting {
        private String greeting;
        private String meaning;
    }

    @Data
    public static class Reference{
        private String author;
        private String title;
        private String date;
        private String link;
    }
}
