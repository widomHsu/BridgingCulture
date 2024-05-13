package edu.monash.bridgingculture.service.utils;

import com.google.gson.Gson;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.investment.*;
import edu.monash.bridgingculture.service.entity.quiz.GeoLocation;
import edu.monash.bridgingculture.service.entity.quiz.TripAdvisor;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for making HTTP requests.
 */
@Component
@Slf4j
@Order(2)
public class HttpUtil {
    @Resource
    OkHttpClient client;
    @Resource
    Gson gson;
    @Resource
    Environment env;
//    @Resource
//    APIUtils apiUtils;

    /**
     * Retrieves information from TripAdvisor based on suburb and country.
     *
     * @param suburb String representing the suburb
     * @param country String representing the country
     * @return TripAdvisor object containing information from TripAdvisor API
     */
    public TripAdvisor getTripAdvisor(String suburb, String country){
        // 1. get geolocation
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/"
                + suburb + ".json"
                + "?country=au"
                + "&limit=1"
                + "&access_token=" + env.getProperty("mapbox.apikey");;
        List<Double> geo = getRequest(url, GeoLocation.class).getFeatures().get(0).getCenter();
        String latLong = geo.get(1) + "," + geo.get(0);

        // 2. get advice
        url = "https://api.content.tripadvisor.com/api/v1/location/search"
                + "?key=" + env.getProperty("tripAdvisor.apikey")
                + "&searchQuery=" + country
                + "&address=" + suburb
                + "&latLong=" + latLong
                + "&language=en";
        return getRequest(url, TripAdvisor.class);
    }

    /**
     * Retrieves festivals for a specific country and year, optionally filtered by type.
     *
     * @param country The country for which festivals are to be retrieved.
     * @param year    The year for which festivals are to be retrieved.
     * @param type    The type of festivals to filter by. Pass null if no filtering by type is needed.
     * @return A list of Festival objects representing the retrieved festivals.
     */
    public List<Festival> getFestivalByCountry(String country, int year, @Nullable String type) {
        String url = "https://api.api-ninjas.com/v1/holidays?country=" + country + "&year=" + year;
        if(type != null)
            url += "&type=" + type;
        List list = getRequest(url, List.class, null);
        List<Festival> festivals = new ArrayList<>();

        for (Object o : list) {
            festivals.add(gson.fromJson(gson.toJson(o), Festival.class));
        }
        return festivals;
    }

    /**
     * Retrieves market time series data from Yahoo Finance API.
     *
     * @param interval Interval for data retrieval.
     * @param range    Range for data retrieval.
     * @return YahooMarketDTO containing market time series data.
     */
    public YahooMarketDTO getMarketTimeSerial(String interval, String range){
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/%5EAXJO" +
                "?interval=" + interval +
                "&range=" + range;
        return getRequest(url, YahooMarketDTO.class);
    }

    /**
     * Retrieves stock time series data from Yahoo Finance API.
     *
     * @param symbol   Symbol of the stock.
     * @param interval Interval for data retrieval.
     * @param range    Range for data retrieval.
     * @return YahooStockDTO containing stock time series data.
     */
    public YahooStockDTO getStockTimeSerial(String symbol, String interval, String range){
        String url = "https://query1.finance.yahoo.com/v8/finance/chart" +
                "/" + symbol +
                "?interval=" + interval +
                "&range=" + range;
        return getRequest(url, YahooStockDTO.class);
    }

//    public TopStockDTO getTopStockByDay(int top){
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, env.getProperty("TopStock"));
//        Request request = new Request.Builder()
//                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/screeners/list" +
//                        "?quoteType=EQUITY" +
//                        "&sortField=percentchange" +
//                        "&region=AU" +
//                        "&size=" + top +
//                        "&offset=0" +
//                        "&sortType=DESC")
//                .post(body)
//                .addHeader("content-type", "application/json")
//                .addHeader("X-RapidAPI-Key", apiUtils.getXRapidAPIKey())
//                .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
//                .build();
//        return getRequest(request, TopStockDTO.class);
//    }

    /**
     * Retrieves top stocks by day using Yahoo Finance API.
     *
     * @return YahooScreenerDTO containing top stock data.
     */
    public YahooScreenerDTO getTopStockByDay(){
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, env.getProperty("TopStockByDay"));
        Request request = new Request.Builder()
                .url("https://query2.finance.yahoo.com/v1/finance/screener?crumb=ez%2FFFFwzw3T&lang=en-US&region=au")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cookie", env.getProperty("TopStockCookie"))
                .build();
        return getRequest(request, YahooScreenerDTO.class);
    }

//    public TopStockYearDTO getTopStockByYear(int top){
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, env.getProperty("TopStock"));
//        Request request = new Request.Builder()
//                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/screeners/list" +
//                        "?quoteType=EQUITY" +
//                        "&sortField=fiftytwowkpercentchange" +
//                        "&region=AU" +
//                        "&size=" + top +
//                        "&offset=0" +
//                        "&sortType=DESC")
//                .post(body)
//                .addHeader("content-type", "application/json")
//                .addHeader("X-RapidAPI-Key", apiUtils.getXRapidAPIKey())
//                .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
//                .build();
//        return getRequest(request, TopStockYearDTO.class);
//    }

    /**
     * Retrieves top stocks by year using Yahoo Finance API.
     *
     * @return YahooScreenerDTO containing top stock data.
     */
    public YahooScreenerDTO getTopStockByYear(){
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, env.getProperty("TopStockByYear"));
        Request request = new Request.Builder()
                .url("https://query2.finance.yahoo.com/v1/finance/screener?crumb=ez%2FFFFwzw3T&lang=en-US&region=au")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cookie", env.getProperty("TopStockCookie"))
                .build();
        return getRequest(request, YahooScreenerDTO.class);
    }

    /**
     * Makes a GET request and retrieves response as an object of specified class.
     *
     * @param url String representing the URL to make the GET request
     * @param clazz Class representing the expected response type
     * @param <T> Type of the expected response object
     * @return Response object of specified class
     */
    public <T> T getRequest(String url, Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return getRequest(request, clazz);
    }

    public <T> T getRequest(String url, Class<T> clazz, String header) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-Api-Key", env.getProperty("festival.apikey"))
                .build();
        return getRequest(request, clazz);
    }

    public <T> T getRequest(Request request, Class<T> clazz) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return gson.fromJson(response.body().string(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
