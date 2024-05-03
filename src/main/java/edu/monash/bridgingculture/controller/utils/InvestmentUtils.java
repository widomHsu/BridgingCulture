package edu.monash.bridgingculture.controller.utils;

import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InvestmentUtils implements ApplicationRunner {

    @Resource
    Environment env;
    Set<String> rangeSet;
    Set<String> intervalSet;

    @Override
    public void run(ApplicationArguments args) {
        List<String> range = Arrays.asList(env.getProperty("YahooFinance_range").split(","));
        rangeSet = new HashSet<>(range);
        List<String> interval = Arrays.asList(env.getProperty("YahooFinance_interval").split(","));
        intervalSet = new HashSet<>(interval);
    }

    public boolean isValidInterval(String interval){
        return intervalSet.contains(interval);
    }
    public boolean isValidRange(String range){
        return rangeSet.contains(range);
    }
}