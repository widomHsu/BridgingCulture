package edu.monash.bridgingculture.service.utils;

import edu.monash.bridgingculture.intf.mapper.APIMapper;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Getter
@Order(1)
@Deprecated
public class APIUtils implements ApplicationRunner {

    @Resource
    APIMapper apiMapper;
    String XRapidAPIKey;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        XRapidAPIKey = apiMapper.getXRapidAPIKey();
    }
}
