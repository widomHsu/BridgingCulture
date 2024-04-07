package edu.monash.bridgingculture.service.entity.quiz;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Census {

    private String ethnic;
    private String suburb;
    private Integer population;
    private Float percentage;
}
