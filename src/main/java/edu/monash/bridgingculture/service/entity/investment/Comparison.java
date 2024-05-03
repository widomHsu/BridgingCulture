package edu.monash.bridgingculture.service.entity.investment;

import lombok.Data;

@Data
public class Comparison {
    Integer timestamp;
    Double stockPrice;
    Double marketPrice;
}
