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

    /**
     * Retrieves positions by quarter.
     *
     * @return A list of PositionQuarter objects representing positions by quarter.
     */
    @Override
    public List<PositionQuarter> getPositionByQuarter() {
        return financeMapper.getPositionByQuarter();
    }

    /**
     * Retrieves positions by year.
     *
     * @return A list of PositionYear objects representing positions by year.
     */
    @Override
    public List<PositionYear> getPositionByYear() {
        return financeMapper.getPositionByYear();
    }

    /**
     * Retrieves super contributions by year.
     *
     * @return A list of SuperContributionsYear objects representing super contributions by year.
     */
    @Override
    public List<SuperContributionsYear> getSuperByYear() {
        return financeMapper.getSuperByYear();
    }

    /**
     * Retrieves super contributions by quarter.
     *
     * @return A list of SuperContributionsQuarter objects representing super contributions by quarter.
     */
    @Override
    public List<SuperContributionsQuarter> getSuperByQuarter() {
        return financeMapper.getSuperByQuarter();
    }

    /**
     * Retrieves bank interest rates.
     *
     * @return A list of BankInterest objects representing bank interest rates.
     */
    @Override
    public List<BankInterest> getBankInterest() {
        return financeMapper.getBankInterest();
    }

}
