package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;
import java.util.List;

@Data
public class Culture {
    private String country;
    private String about;
    private List<DemographicInfo> demographicInfos;
    private String traditionalClothing;
    private String greetings;
    private String cuisinesInfo;
    private List<CuisineImages> cuisineImages;
    private String caveatInfo;
    private List<FestivalInfo> festivalInfos;
    private List<String> languageInfos;
    private List<CulturalEtiquette> culturalEtiquettes;
}
