package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.InvestmentService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.investment.*;
import edu.monash.bridgingculture.service.utils.HttpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class InvestmentServiceImpl implements InvestmentService {

    @Resource
    HttpUtil httpUtil;
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public ResponseDO getStockAndMarket(String symbol, String interval, String range) {
        Future<MarketDTO> submit1 = executorService.submit(() -> httpUtil.getMarket(interval, range));
        Future<StockDTO> submit2 = executorService.submit(() -> httpUtil.getStock(symbol, interval, range));

        MarketDTO marketDTO = null;
        StockDTO stockDTO = null;
        try {
            marketDTO = submit1.get();
            stockDTO = submit2.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Integer> timestamp = marketDTO.getAxjo().getTimestamp();

        List<StockDTO.ChartDTO.ResultDTO> results = stockDTO.getChart().getResult();
        if(results == null)
            return ResponseDO.fail("Invalid stock symbol.");

        StockDTO.ChartDTO.ResultDTO resultDTO = results.get(0);
        List<Integer> stockTimeStamp = resultDTO.getTimestamp();
        List<Double> close = resultDTO.getIndicators().getQuote().get(0).getClose();
        int size = resultDTO.getTimestamp().size();

        Map<Integer, Double> map = new HashMap<>(size);
        for(int i = 0; i < stockTimeStamp.size(); i++){
            map.put(stockTimeStamp.get(i), close.get(i));
        }

        List<Comparison> comparisons = new ArrayList<>();
        for(int i = 0; i < timestamp.size(); i++){
            Comparison comparison = new Comparison();
            Integer t = timestamp.get(i);
            comparison.setTimestamp(t);
            comparison.setStockPrice(map.get(t));
            comparison.setMarketPrice(marketDTO.getAxjo().getClose().get(i));
            comparisons.add(comparison);
        }
        return ResponseDO.success(comparisons);
    }

    @Override
    public List<StockChange> getTopStock(int top) {
        TopStockDTO topStockDTO = httpUtil.getTopStock(top);
        List<TopStockDTO.FinanceDTO.ResultDTO.QuotesDTO> quotes = topStockDTO.getFinance().getResult().get(0).getQuotes();
        List<StockChange> res = new ArrayList<>();
        for(TopStockDTO.FinanceDTO.ResultDTO.QuotesDTO quote: quotes){
            StockChange stockChange = new StockChange();
            stockChange.setName(quote.getLongName());
            stockChange.setSymbol(quote.getSymbol());
            stockChange.setPricePreviousClose(quote.getRegularMarketPreviousClose().getFmt());
            stockChange.setPriceNow(quote.getRegularMarketPrice().getFmt());
            stockChange.setPriceChange(quote.getRegularMarketChange().getFmt());
            stockChange.setChangePercent(quote.getRegularMarketChangePercent().getFmt());
            res.add(stockChange);
        }
        return res;
    }
}
