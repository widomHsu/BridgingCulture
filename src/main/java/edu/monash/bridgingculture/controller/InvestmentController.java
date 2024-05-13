package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.utils.InvestmentUtils;
import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.intf.InvestmentService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class InvestmentController {

    @Resource
    InvestmentService investmentService;
    @Resource
    InvestmentUtils investmentUtils;

    /**
     * Retrieves top stock by day.
     *
     * @param top Number of top stocks to retrieve.
     * @return Response containing the top stocks for the day.
     */
    @GetMapping("/stock/day")
    @Log
    public ResponseDO getTopStockByDay(@RequestParam("top") String top){
        return ResponseDO.success(investmentService.getTopStockByDay(Integer.parseInt(top)));
    }

    /**
     * Retrieves top stock by year.
     *
     * @param top Number of top stocks to retrieve.
     * @return Response containing the top stocks for the year.
     */
    @GetMapping("/stock/year")
    @Log
    public ResponseDO getTopStockByYear(@RequestParam("top") String top){
        return ResponseDO.success(investmentService.getTopStockByYear(Integer.parseInt(top)));
    }

    /**
     * Retrieves stock and market data.
     *
     * @param symbol   Symbol of the stock.
     * @param interval Interval for data retrieval.
     * @param range    Range for data retrieval.
     * @return Response containing stock and market data.
     */
    @GetMapping("/stockAndMarket")
    @Log
    public ResponseDO getStockAndMarket(@RequestParam("symbol") String symbol,
                                        @RequestParam("interval") String interval,
                                        @RequestParam("range") String range){
        if(StringUtils.isEmpty(symbol) || StringUtils.isEmpty(interval) || StringUtils.isEmpty(range))
            return ResponseDO.fail("At least one parameter is empty.");
        if(!investmentUtils.isValidInterval(interval))
            return ResponseDO.fail("Invalid interval.");
        if(!investmentUtils.isValidRange(range))
            return ResponseDO.fail("Invalid range.");

        return investmentService.getStockAndMarket(symbol, interval, range);
    }

    /**
     * Retrieves market price.
     *
     * @return Response containing the market price.
     */
    @GetMapping("/market")
    public ResponseDO getMarket(){
        return ResponseDO.success(investmentService.getMarketPrice());
    }
}
