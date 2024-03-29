package edu.monash.bridgingculture.service.entity;

import lombok.Data;

@Data
public class Census {
    /**
     * CREATE TABLE top5_suburbs_VIC (
     *   country VARCHAR2(50) NOT NULL,
     *   suburb VARCHAR2(50) NOT NULL,
     *   population NUMBER,
     *   percentage NUMBER
     * );
     */

    private String ethnic;
    private String suburb;
    private Integer population;
    private Float percentage;
}
