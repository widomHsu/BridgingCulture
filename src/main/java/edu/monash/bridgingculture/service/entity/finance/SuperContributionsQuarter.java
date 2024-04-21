package edu.monash.bridgingculture.service.entity.finance;

import lombok.Data;

@Data
public class SuperContributionsQuarter {

    /**
     * CREATE TABLE `super_contributions_quarter` (
     *                                          `type` VARCHAR(255) NOT NULL,
     *                                          `date` VARCHAR(255) NOT NULL,
     *                                          `value` INT NOT NULL
     * );
     */
    private String type;
    private String date;
    private Integer value;
}
