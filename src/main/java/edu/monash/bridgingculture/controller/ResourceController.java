package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.intf.ResourceService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
@Slf4j
public class ResourceController {

    @Resource
    ResourceService resourceService;
    @GetMapping("/resource")
    public ResponseDO getResource(@RequestParam("country") String country){

        return resourceService.getResource(country);
    }
}
