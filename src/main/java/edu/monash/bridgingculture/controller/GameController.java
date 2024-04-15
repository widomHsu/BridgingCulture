package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.intf.GameService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
/**
 * This class represents a controller for managing game-related operations.
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Resource
    GameService gameService;

    /**
     * Retrieves items related to the game.
     * @return ResponseDO containing the success status and retrieved items
     */
    @GetMapping("/items")
    @Log
    public ResponseDO getItems(){
        return ResponseDO.success(gameService.getItems());
    }
}
