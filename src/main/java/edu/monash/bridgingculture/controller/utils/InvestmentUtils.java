package edu.monash.bridgingculture.controller.utils;

import edu.monash.bridgingculture.intf.InvestmentService;
import edu.monash.bridgingculture.intf.mapper.InvestmentMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for investment-related operations, including validating intervals and ranges.
 * Implements ApplicationRunner to perform initialization tasks on application startup.
 */
@Component
@Slf4j
public class InvestmentUtils implements ApplicationRunner {

    @Resource
    Environment env;
    @Resource
    InvestmentService investmentService;
    @Resource
    InvestmentMapper investmentMapper;
    Set<String> rangeSet;
    Set<String> intervalSet;

    /**
     * Initializes ranges and intervals sets and triggers an update if necessary on application startup.
     *
     * @param args Application arguments.
     */
    @Override
    public void run(ApplicationArguments args) {
        List<String> range = Arrays.asList(env.getProperty("YahooFinance_range").split(","));
        rangeSet = new HashSet<>(range);
        List<String> interval = Arrays.asList(env.getProperty("YahooFinance_interval").split(","));
        intervalSet = new HashSet<>(interval);
        if(investmentMapper.getNoOfRow() == 0)
            investmentService.updateTopStockByYear();
        else
            log.info("Today's data has been updated");
    }

    /**
     * Checks if the provided interval is valid.
     *
     * @param interval Interval to validate.
     * @return True if the interval is valid, false otherwise.
     */
    public boolean isValidInterval(String interval){
        return intervalSet.contains(interval);
    }

    /**
     * Checks if the provided range is valid.
     *
     * @param range Range to validate.
     * @return True if the range is valid, false otherwise.
     */
    public boolean isValidRange(String range){
        return rangeSet.contains(range);
    }
}