package edu.monash.bridgingculture.service.entity.finance;

import lombok.Data;

@Data
public class PositionYear {

    /**
     * CREATE TABLE financial_position_annual (
     *     date_year INT,
     *     total_assets FLOAT,
     *     number_of_entities INT
     * );
     */

    private Integer year;
    private Float totalAssets;
    private Integer numberOfEntities;
}
