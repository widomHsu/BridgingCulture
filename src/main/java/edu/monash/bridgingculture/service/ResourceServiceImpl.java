package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.ResourceService;
import edu.monash.bridgingculture.intf.mapper.ResourceMapper;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.resource.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    ResourceMapper resourceMapper;
    @Override
    public ResponseDO getResource(String country) {
        CulturalInfo culturalInfo = resourceMapper.getCulturalInfo(country);
        List<DemographicInfo> demographicInfo = resourceMapper.getDemographicInfo(country);
        List<FestivalInfo> festivalInfo = resourceMapper.getFestivalInfo(country);
        List<String> languageInfo = resourceMapper.getLanguageInfo(country);
        List<CuisineImages> cuisineImages = resourceMapper.getCuisineImages(country);
        List<CulturalEtiquette> culturalEtiquette = resourceMapper.getCulturalEtiquette(country);
        Culture culture = new Culture();
        culture.setCountry(country);
        culture.setAbout(culturalInfo.getAbout());
        culture.setDemographicInfos(demographicInfo);
        culture.setTraditionalClothing(culturalInfo.getTraditionalClothing());
        culture.setGreetings(culturalInfo.getGreetings());
        culture.setCuisinesInfo(culturalInfo.getCuisinesInfo());
        culture.setCuisineImages(cuisineImages);
        culture.setCaveatInfo(culturalInfo.getCaveatInfo());
        culture.setFestivalInfos(festivalInfo);
        culture.setLanguageInfos(languageInfo);
        culture.setCulturalEtiquettes(culturalEtiquette);
        return ResponseDO.success(culture);
    }
}
