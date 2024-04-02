package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.intf.GameService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    @Resource
    GameService gameService;
    @GetMapping("/items")
    public ResponseDO getItems(){
        return gameService.getItems();
    }
}
