package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.intf.FinanceService;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class FinanceController {

    @Resource
    FinanceService financeService;

    /**
     * Handles HTTP GET requests to retrieve positions by quarter.
     *
     * @return A ResponseDO object containing the positions by quarter.
     */
    @GetMapping("/position/quarter")
    public ResponseDO getPositionByQuarter(){
        return ResponseDO.success(financeService.getPositionByQuarter());
    }

    /**
     * Handles HTTP GET requests to retrieve positions by year.
     *
     * @return A ResponseDO object containing the positions by year.
     */
    @GetMapping("/position/year")
    public ResponseDO getPositionByYear(){
        return ResponseDO.success(financeService.getPositionByYear());
    }

    /**
     * Handles HTTP GET requests to retrieve super contributions by year.
     *
     * @return A ResponseDO object containing the super contributions by year.
     */
    @GetMapping("/super/year")
    public ResponseDO getSuperByYear(){
        return ResponseDO.success(financeService.getSuperByYear());
    }

    /**
     * Handles HTTP GET requests to retrieve super contributions by quarter.
     *
     * @return A ResponseDO object containing the super contributions by quarter.
     */
    @GetMapping("/super/quarter")
    public ResponseDO getSuperByQuarter(){
        return ResponseDO.success(financeService.getSuperByQuarter());
    }

    /**
     * Handles HTTP GET requests to retrieve bank interest rates.
     *
     * @return A ResponseDO object containing the bank interest rates.
     */
    @GetMapping("/bank/interest")
    public ResponseDO getBankInterest(){
        return ResponseDO.success(financeService.getBankInterest());
    }

}
