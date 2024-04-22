package edu.monash.bridgingculture.service.entity.festival;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Reminder {

    @Data
    public static class RequestDO{
        String email;
        Integer id;
        String aheadNumber;
        UnitType unitType;
    }

    @AllArgsConstructor
    public enum UnitType{
        hour(1),
        day(24),
        week(7*24);
        private final int aheadHours;

        public int getAheadHours() {
            return aheadHours;
        }
    }
}
