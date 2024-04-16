package edu.monash.bridgingculture.service.entity.festival;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Festival{

    private String country;
    private String iso;
    private Integer year;
    private String date;
    private String day;
    private String name;
    private String type;
}
