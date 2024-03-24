package edu.monash.bridgingculture.service.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class HttpClient {
    @Resource
    OkHttpClient client;
    @Resource
    Gson gson;
//    @Value("${festival.key}")
//    String key;
    @Resource
    Environment env; // env.getProperty("festival.key");

}
