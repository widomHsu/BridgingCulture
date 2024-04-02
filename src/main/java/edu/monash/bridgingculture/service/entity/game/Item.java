package edu.monash.bridgingculture.service.entity.game;

import lombok.Data;
import lombok.NoArgsConstructor;

// for game
@Data
@NoArgsConstructor
public class Item {
    private Integer ID;
    private String imageURL;
    private String country;
    private String signification;
    private String questionContent;
    private String options;
    private String correctOption;
}
