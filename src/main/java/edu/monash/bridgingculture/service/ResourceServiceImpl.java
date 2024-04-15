package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.ResourceService;
import edu.monash.bridgingculture.intf.mapper.ResourceMapper;
import edu.monash.bridgingculture.service.entity.resource.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * Implementation of the ResourceService interface providing resource-related functionality.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    ResourceMapper resourceMapper;

    /**
     * Retrieves cultural information and resources related to the specified country.
     *
     * @param country String representing the country for which cultural information is requested
     * @return Culture object containing cultural information and resources
     */
    @Override
    public Culture getResource(String country) {
        Culture.CulturalInfo culturalInfo = resourceMapper.getCulturalInfo(country);
        if(culturalInfo == null)
            return null;
        List<Culture.DemographicInfo> demographicInfo = resourceMapper.getDemographicInfo(country);
        List<Culture.FestivalInfo> festivalInfo = resourceMapper.getFestivalInfo(country);
        List<String> languageInfo = resourceMapper.getLanguageInfo(country);
        List<String> cuisineImages = resourceMapper.getCuisineImages(country);
        List<Culture.CulturalEtiquette> culturalEtiquette = resourceMapper.getCulturalEtiquette(country);
        List<Culture.Greeting> greetings = resourceMapper.getGreetings(country);
        List<Culture.Reference> references = resourceMapper.getReferences(country);

        Culture culture = new Culture();

        culture.setCountry(country);
        culture.setAbout(culturalInfo.getAbout());
        culture.setDemographicInfos(demographicInfo);
        culture.setTraditionalClothing(culturalInfo.getTraditionalClothing());
        culture.setGreetings(greetings);
        culture.setCuisinesInfo(culturalInfo.getCuisinesInfo());
        culture.setCuisineImages(cuisineImages);
        culture.setCaveatInfo(culturalInfo.getCaveatInfo());
        culture.setFestivalInfos(festivalInfo);
        culture.setLanguageInfos(languageInfo);
        culture.setCulturalEtiquettes(culturalEtiquette);
        culture.setReferenceInfos(references);
        return culture;
    }
}
