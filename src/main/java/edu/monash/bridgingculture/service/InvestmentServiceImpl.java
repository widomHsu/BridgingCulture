package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.InvestmentService;
import edu.monash.bridgingculture.intf.mapper.InvestmentMapper;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.investment.*;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static edu.monash.bridgingculture.service.utils.ThreadPoolUtils.executorService;

@Service
@Slf4j
public class InvestmentServiceImpl implements InvestmentService {

    @Resource
    HttpUtil httpUtil;
    @Resource
    InvestmentMapper investmentMapper;
    DecimalFormat percentFormatter = new DecimalFormat("0.00%");
    DecimalFormat radixFormatter = new DecimalFormat("0.0000");

    @Override
    public ResponseDO getStockAndMarket(String symbol, String interval, String range) {
        Future<YahooMarketDTO> submit1 = executorService.submit(() -> httpUtil.getMarketTimeSerial(interval, range));
        Future<YahooStockDTO> submit2 = executorService.submit(() -> httpUtil.getStockTimeSerial(symbol, interval, range));

        YahooMarketDTO marketDTO = null;
        YahooStockDTO stockDTO = null;
        try {
            marketDTO = submit1.get();
            stockDTO = submit2.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(marketDTO.getChart().getResult() == null || stockDTO.getChart().getResult() == null)
            return ResponseDO.fail("Invalid stock symbol or wrong range.");

        YahooMarketDTO.ChartDTO.ResultDTO marketResultDTO = marketDTO.getChart().getResult().get(0);
        List<Integer> timestamp = marketResultDTO.getTimestamp();
        YahooStockDTO.ChartDTO.ResultDTO stockResultDTO = stockDTO.getChart().getResult().get(0);

        List<Integer> stockTimeStamp = stockResultDTO.getTimestamp();
        List<Double> close = stockResultDTO.getIndicators().getQuote().get(0).getClose();
        int size = stockResultDTO.getTimestamp().size();

        Map<Integer, String> map = new HashMap<>(size);
        log.info(symbol + " size: " + stockTimeStamp.size());
        for(int i = 0; i < stockTimeStamp.size(); i++){
            map.put(stockTimeStamp.get(i), close.get(i) != null?radixFormatter.format(close.get(i)) : null);
        }

        List<Comparison> comparisons = new ArrayList<>(timestamp.size());
        log.info("Market size: " + timestamp.size());
        for(int i = 0; i < timestamp.size(); i++){
            Comparison comparison = new Comparison();
            Integer t = timestamp.get(i);
            comparison.setTimestamp(t);
            comparison.setStockPrice(map.get(t));
            Double marketPrice = marketResultDTO.getIndicators().getQuote().get(0).getClose().get(i);
            comparison.setMarketPrice(marketPrice != null? radixFormatter.format(marketPrice) : null);
            comparisons.add(comparison);
        }
        return ResponseDO.success(comparisons);
    }

    @Override
    public MarketSummary getMarketPrice() {
        YahooMarketDTO marketTimeSerial = httpUtil.getMarketTimeSerial("1m", "1d");
        YahooMarketDTO.ChartDTO.ResultDTO resultDTO = marketTimeSerial.getChart().getResult().get(0);

        BigDecimal price = BigDecimal.valueOf(resultDTO.getMeta().getRegularMarketPrice());
        BigDecimal prePrice = BigDecimal.valueOf(resultDTO.getMeta().getPreviousClose());
        BigDecimal subtract = price.subtract(prePrice);
        BigDecimal divide = subtract.divide(prePrice, 4, BigDecimal.ROUND_HALF_UP);

        MarketSummary marketSummary = new MarketSummary();
        marketSummary.setRegularMarketPrice(String.valueOf(price));
        marketSummary.setRegularMarketChange(subtract.toString());
        marketSummary.setRegularMarketChangePercent(percentFormatter.format(divide));
        return marketSummary;
    }

    @Override
    public List<StockChangeInADay> getTopStockByDay(int top) {
        YahooScreenerDTO stockByDay = httpUtil.getTopStockByDay();
        List<YahooScreenerDTO.FinanceDTO.ResultDTO.QuotesDTO> quotes = stockByDay.getFinance().getResult().get(0).getQuotes();
        List<StockChangeInADay> res = new ArrayList<>();
        for(YahooScreenerDTO.FinanceDTO.ResultDTO.QuotesDTO quote: quotes){
            StockChangeInADay stockChangeInADay = new StockChangeInADay();
            stockChangeInADay.setName(quote.getLongName() != null? quote.getLongName() : quote.getShortName());
            stockChangeInADay.setSymbol(quote.getSymbol());

            BigDecimal previousPrice = BigDecimal.valueOf(quote.getRegularMarketPreviousClose());
            stockChangeInADay.setPricePreviousClose(radixFormatter.format(previousPrice));

            BigDecimal marketPrice = BigDecimal.valueOf(quote.getRegularMarketPrice());
            stockChangeInADay.setPriceNow(radixFormatter.format(marketPrice));

            BigDecimal priceChange = BigDecimal.valueOf(quote.getRegularMarketChange());
            stockChangeInADay.setPriceChange(radixFormatter.format(priceChange));

            BigDecimal changePercent = BigDecimal.valueOf(quote.getRegularMarketChangePercent() / 100);
            stockChangeInADay.setChangePercent(percentFormatter.format(changePercent));

            res.add(stockChangeInADay);
            if(res.size() == top)
                break;
        }
        return res;
    }

    @Override
    public List<StockChangeInAYear> getTopStockByYear(int top) {
        return investmentMapper.getTopStock(top);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateTopStockByYear() {
        YahooScreenerDTO stockByYear = httpUtil.getTopStockByYear();
        List<YahooScreenerDTO.FinanceDTO.ResultDTO.QuotesDTO> quotes = stockByYear.getFinance().getResult().get(0).getQuotes();
        List<StockChangeInAYear> res = new ArrayList<>();

        for(YahooScreenerDTO.FinanceDTO.ResultDTO.QuotesDTO quote: quotes){
            StockChangeInAYear stockChange = new StockChangeInAYear();
            stockChange.setName(quote.getLongName() != null? quote.getLongName() : quote.getShortName());
            stockChange.setSymbol(quote.getSymbol());

            BigDecimal highest = BigDecimal.valueOf(quote.getFiftyTwoWeekHigh());
            BigDecimal lowest = BigDecimal.valueOf(quote.getFiftyTwoWeekLow());

            stockChange.setPriceLowest(radixFormatter.format(lowest));
            stockChange.setPriceHighest(radixFormatter.format(highest));

            BigDecimal subtract = highest.subtract(lowest);
            BigDecimal divide = subtract.divide(lowest, 4, BigDecimal.ROUND_HALF_UP);

            stockChange.setPriceChange(radixFormatter.format(subtract));
            stockChange.setChangePercent(percentFormatter.format(divide));
            res.add(stockChange);
        }
        int truncateNo = investmentMapper.truncateTopStock();
        log.info("Truncate " + truncateNo + " row data");
        res.sort((a, b) -> Double.compare(Double.parseDouble(b.getChangePercent().replace("%", "")),
                Double.parseDouble(a.getChangePercent().replace("%", ""))));
        int insertNo = investmentMapper.insertTopStock(res);
        log.info("Insert " + insertNo + " row data");
    }

}
