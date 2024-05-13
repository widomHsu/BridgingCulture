package edu.monash.bridgingculture.service.entity.job;

import lombok.Data;

import java.util.List;

@Data
public class Job {
    String job1_title = "Participation and unemployment rates in Victoria (2013-2023)";
    List<Job1Employment> job1_participationRate;
    String job2_title = "Top 20 occupations in demand, new workers expected in Victoria(2024-2026)";
    List<Job2Top20Occupations> job2_topOccupations;
    String job3_title = "New workers expected in Victoria by industry(2024-2026)";
    List<Job3Industry> job3_newWorkersByIndustry;
    String job4_title = "Proportion of total employment by industry in Victoria";
    List<Job4Proportion> job4_proportionByIndustry;
    String job5_title = "Occupations in demand that require higher-order skills in victoria, new workers expected(2024-2026)";
    List<Job5Occupations> job5_demandBySkill;
    String job6_title = "Full-time and part-time employment trends in Victoria(2018-2023)";
    List<Job6FullPart> job6_fullAndPart;
    String job7_title = "Change in employment by industry in Victoria(2013-2023)";
    List<Job7Changing> job7_changeByIndustry;
    String job8_title = "New workers expected in Victoria by skill level(2024-2026)";
    List<Job8SkillLevel> job8_newWorkersBySkill;
    String job9_title = "New workers expected across regional Victoria(2024-2026)";
    List<Job9NewWorkers> job9_newWorkersByRegion;
    String job10_title = "New workers expected by industry(2024-2026)";
    List<Job10WorkersIndustry> job10_newWorkersByIndustry;

    @Data
    public static class Job1Employment {
        int year;
        private double unemploymentRate;
        private double participationRate;
    }

    @Data
    public static class Job2Top20Occupations {
        private String occupation;
        private int numberInDemand;
    }

    @Data
    public static class Job3Industry {
        private String industry;
        private double higherOrderSkillsPercentage;
        private double otherSkillsPercentage;
        private int newWorkersExpected;
    }

    @Data
    public static class Job4Proportion {
        private String sector;
        private String industry;
        private double percentage;
    }

    @Data
    public static class Job5Occupations {
        private String industry;
        private String occupation;
        private int numberInDemand;
    }

    @Data
    public static class Job6FullPart {
        private int year;
        private double fullTime;
        private double partTime;
    }

    @Data
    public static class Job7Changing {
        private String industry;
        private int victoriaChange;
        private double victoriaPercentage;
        private int australiaChange;
        private double australiaPercentage;
    }

    @Data
    public static class Job8SkillLevel {
        private String skillLevel;
        private int newWorkers;
        private String skillCategory;
    }

    @Data
    public static class Job9NewWorkers {
        private String region;
        private int newWorkersExpected;
    }

    @Data
    public static class Job10WorkersIndustry {
        private String occupation;
        private int numberInDemand;
    }
}
