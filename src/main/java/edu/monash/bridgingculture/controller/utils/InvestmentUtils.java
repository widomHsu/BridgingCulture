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

    public boolean isValidInterval(String interval){
        return intervalSet.contains(interval);
    }
    public boolean isValidRange(String range){
        return rangeSet.contains(range);
    }
}