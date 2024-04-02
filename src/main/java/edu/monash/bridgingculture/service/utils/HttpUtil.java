package edu.monash.bridgingculture.service.utils;

import com.google.gson.Gson;
import edu.monash.bridgingculture.service.entity.quiz.GeoLocation;
import edu.monash.bridgingculture.service.entity.quiz.TripAdvisor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class HttpUtil {
    @Resource
    OkHttpClient client;
    @Resource
    Gson gson;
    @Resource
    Environment env;

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

    public <T> T getRequest(String url, Class<T> clazz){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try(Response response = client.newCall(request).execute()){
            return gson.fromJson(response.body().string(), clazz);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
