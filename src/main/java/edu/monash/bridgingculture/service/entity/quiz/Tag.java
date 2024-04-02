package edu.monash.bridgingculture.service.entity.quiz;

import lombok.Data;

// used to judge the favorite country
@Data
public class Tag {
    private String country;
    private String tags; // separated by &
}
