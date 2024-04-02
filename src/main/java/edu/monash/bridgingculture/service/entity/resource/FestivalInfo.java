package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;

@Data
public class FestivalInfo {

    /**
     *     country VARCHAR(100),
     *     festival VARCHAR(255),
     *     significance VARCHAR(255),
     *     image_url VARCHAR(255)
     */

    private String festival;
    private String significance;
    private String imageUrl;
}
