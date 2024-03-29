package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.intf.GameService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/game")
public class GameController {

    @Resource
    GameService gameService;

    @GetMapping
    public ResponseDO getResource(){

        return ResponseDO.success(null);
    }

    @GetMapping("/items")
    public ResponseDO getItems(){
        return gameService.getItems();
    }
}
