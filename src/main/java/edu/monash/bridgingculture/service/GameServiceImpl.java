package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.GameService;
import edu.monash.bridgingculture.intf.mapper.GameMapper;
import edu.monash.bridgingculture.service.entity.game.Item;
import edu.monash.bridgingculture.service.entity.game.ItemDO;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.utils.RandomUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the GameService interface providing game-related functionality.
 */
@Service
public class GameServiceImpl implements GameService {

    @Resource
    GameMapper gameMapper;
    @Resource
    RandomUtil randomUtil;

    /**
     * Retrieves a list of randomized game items.
     *
     * @return List of ItemDO representing the randomized game items
     */
    @Override
    public List<ItemDO> getItems() {
        List<Item> items = gameMapper.getItems();
        List<Item> randomItems = randomUtil.getRandomElement(items, 5, 10, 0.3);
        List<ItemDO> list = new ArrayList<>();
        for(Item item: randomItems){
            list.add(dealItems(item));
        }
        return list;
    }

    /**
     * Transforms an Item object into an ItemDO object.
     *
     * @param item Item object to be transformed
     * @return Transformed ItemDO object
     */
    public static ItemDO dealItems(Item item){
        ItemDO itemDO = new ItemDO();

        String[] strings = item.getOptions().split("&");
        itemDO.setOptions(Arrays.asList(strings));

        itemDO.setID(item.getID());
        itemDO.setCountry(item.getCountry());
        itemDO.setSignification(item.getSignification());
        itemDO.setImageURL(item.getImageURL());
        itemDO.setCorrectOption(item.getCorrectOption());
        itemDO.setQuestionContent(item.getQuestionContent());
        return itemDO;
    }
}
