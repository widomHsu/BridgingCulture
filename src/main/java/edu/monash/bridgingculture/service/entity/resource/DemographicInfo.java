package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;

@Data
public class DemographicInfo {
    /**
     *     country VARCHAR(100),
     *     group_ VARCHAR(100),
     *     sub_group VARCHAR(100),
     *     population_number INT,
     *     population_percentage DECIMAL(5,2)
     */

    private String group;
    private String subGroup;
    private Integer populationNumber;
    private Double populationPercentage;
}
