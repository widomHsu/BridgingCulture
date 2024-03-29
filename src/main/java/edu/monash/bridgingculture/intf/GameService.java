package edu.monash.bridgingculture.intf;


import edu.monash.bridgingculture.service.entity.ResponseDO;

public interface GameService {
    ResponseDO getItems();

    ResponseDO getResource();
}
