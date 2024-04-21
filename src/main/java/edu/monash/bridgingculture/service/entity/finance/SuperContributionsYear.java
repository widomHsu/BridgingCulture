package edu.monash.bridgingculture.service.entity.finance;

import lombok.Data;

@Data
public class SuperContributionsYear {

    /**
     * CREATE TABLE superannuation_contributions_year (
     *                                     type VARCHAR(40),
     *                                     year INT,
     *                                     value INT
     * );
     */

    private String type;
    private Integer year;
    private Integer value;
}
