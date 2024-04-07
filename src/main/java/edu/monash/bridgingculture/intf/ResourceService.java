package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.resource.Culture;

public interface ResourceService {
    Culture getResource(String country);
}
