package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;

@Data
public class CulturalEtiquette {
    /**
     *     country VARCHAR(100),
     *     can VARCHAR(255),
     *     cannot VARCHAR(255)
     */
    private String can;
    private String cannot;
}
