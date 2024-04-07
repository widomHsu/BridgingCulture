package edu.monash.bridgingculture.intf;


import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.entity.game.Item;
import edu.monash.bridgingculture.service.entity.game.ItemDO;

import java.util.List;

public interface GameService {
    List<ItemDO> getItems();
}
