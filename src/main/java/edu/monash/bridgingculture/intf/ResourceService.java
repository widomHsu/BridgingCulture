package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.ResponseDO;

public interface ResourceService {
    ResponseDO getResource(String country);
}
