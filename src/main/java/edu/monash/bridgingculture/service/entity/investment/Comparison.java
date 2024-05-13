package edu.monash.bridgingculture.service.entity.investment;

import lombok.Data;

@Data
public class Comparison {
    Integer timestamp;
    String stockPrice;
    String marketPrice;
}
