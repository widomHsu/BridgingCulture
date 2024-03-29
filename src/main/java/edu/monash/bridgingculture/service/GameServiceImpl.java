package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.GameService;
import edu.monash.bridgingculture.intf.mapper.GameMapper;
import edu.monash.bridgingculture.service.entity.Item;
import edu.monash.bridgingculture.service.entity.ItemDO;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import edu.monash.bridgingculture.service.utils.RandomUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Resource
    GameMapper gameMapper;
    @Resource
    RandomUtil randomUtil;

    @Override
    public ResponseDO getItems() {
        List<Item> items = gameMapper.getItems();
        List<Item> randomItems = randomUtil.getRandomElement(items, 5, 10, 0.3);
        ArrayList<ItemDO> list = new ArrayList<>();
        for(Item item: randomItems){
            list.add(dealItems(item));
        }
        return ResponseDO.success(list);
    }

    @Override
    public ResponseDO getResource() {

        return null;
    }

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
