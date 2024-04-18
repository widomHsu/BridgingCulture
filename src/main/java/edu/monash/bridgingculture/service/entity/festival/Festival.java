package edu.monash.bridgingculture.service.entity.festival;

import lombok.*;

import java.util.List;

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

    @Data
    public static class FestivalDO{
        List<String> countries;
        String year;
        String month;
        List<String> types;
    }
}
