package edu.monash.bridgingculture.service.entity.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDO {
    private Integer ID;
    private String imageURL;
    private String country;
    private String signification;
    private String questionContent;
    private List<String> options;
    private String correctOption;
}
