package edu.monash.bridgingculture.service.entity.quiz;

import lombok.Data;

// for suburb recommendation
@Data
public class Question {
    private String questionContent;
    private String options; // separated by &
}
