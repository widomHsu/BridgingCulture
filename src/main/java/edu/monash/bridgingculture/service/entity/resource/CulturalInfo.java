package edu.monash.bridgingculture.service.entity.resource;

import lombok.Data;

@Data
public class CulturalInfo {
    /**
     *     country VARCHAR2(100),
     *     about CLOB,
     *     traditional_clothing CLOB,
     *     greetings CLOB,
     *     cuisines_info CLOB,
     *     caveat_info CLOB
     */

    private String country;
    private String about;
    private String traditionalClothing;
    private String greetings;
    private String cuisinesInfo;
    private String caveatInfo;
}
