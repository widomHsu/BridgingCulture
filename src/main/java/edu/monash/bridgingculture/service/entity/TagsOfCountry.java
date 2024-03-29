package edu.monash.bridgingculture.service.entity;

import lombok.Data;

// used to judge the favorite country
@Data
public class TagsOfCountry {
    private String country;
    private String tags; // separated by &
}
