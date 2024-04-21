package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.FinanceService;
import edu.monash.bridgingculture.intf.mapper.FinanceMapper;
import edu.monash.bridgingculture.service.entity.finance.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Resource
    FinanceMapper financeMapper;

    @Override
    public List<PositionQuarter> getPositionByQuarter() {
        return financeMapper.getPositionByQuarter();
    }

    @Override
    public List<PositionYear> getPositionByYear() {
        return financeMapper.getPositionByYear();
    }

    @Override
    public List<SuperContributionsYear> getSuperByYear() {
        return financeMapper.getSuperByYear();
    }

    @Override
    public List<SuperContributionsQuarter> getSuperByQuarter() {
        return financeMapper.getSuperByQuarter();
    }

    @Override
    public List<BankInterest> getBankInterest() {
        return financeMapper.getBankInterest();
    }
}
