package edu.monash.bridgingculture.service.entity.finance;

import lombok.Data;

@Data
public class PositionQuarter {

    /**
     * CREATE TABLE `financial_position` (
     *     `record_date` VARCHAR(255) NOT NULL,
     *     `total_assets` INT NOT NULL,
     *     `number_of_entities` INT NOT NULL
     * );
     */
    private String recordDate;
    private Integer totalAssets;
    private String numberOfEntities;
}
