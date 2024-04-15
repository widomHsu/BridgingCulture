package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.intf.ResourceService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.resource.Culture;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * This class represents a controller for managing resources.
 */
@RestController
@RequestMapping("/")
public class ResourceController {
    @Resource
    ResourceService resourceService;

    /**
     * Retrieves a resource based on the specified country.
     *
     * @param country String representing the country for which the resource is requested
     * @return ResponseDO containing the success status and retrieved resource (Culture object)
     */
    @GetMapping("/resource")
    @Log
    public ResponseDO getResource(@RequestParam("country") String country){
        if(StringUtils.isEmpty(country))
            return ResponseDO.fail("The country is null.");
        Culture culture = resourceService.getResource(country);
        if(culture == null)
            return ResponseDO.fail("The country is invalid.");
        return ResponseDO.success(culture);
    }
}
