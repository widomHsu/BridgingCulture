package edu.monash.bridgingculture.service.utils;

import com.google.gson.Gson;
import edu.monash.bridgingculture.service.entity.festival.Festival;
import edu.monash.bridgingculture.service.entity.quiz.GeoLocation;
import edu.monash.bridgingculture.service.entity.quiz.TripAdvisor;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
public class HttpUtil {
    @Resource
    OkHttpClient client;
    @Resource
    Gson gson;
    @Resource
    Environment env;

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
