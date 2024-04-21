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

    @GetMapping("/position/quarter")
    public ResponseDO getPositionByQuarter(){
        return ResponseDO.success(financeService.getPositionByQuarter());
    }

    @GetMapping("/position/year")
    public ResponseDO getPositionByYear(){
        return ResponseDO.success(financeService.getPositionByYear());
    }

    @GetMapping("/super/year")
    public ResponseDO getSuperByYear(){
        return ResponseDO.success(financeService.getSuperByYear());
    }

    @GetMapping("/super/quarter")
    public ResponseDO getSuperByQuarter(){
        return ResponseDO.success(financeService.getSuperByQuarter());
    }

    @GetMapping("/bank/interest")
    public ResponseDO getBankInterest(){
        return ResponseDO.success(financeService.getBankInterest());
    }
}
