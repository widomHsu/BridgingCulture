package edu.monash.bridgingculture.intf;

import edu.monash.bridgingculture.service.entity.finance.*;

import java.util.List;

public interface FinanceService {

    List<PositionQuarter> getPositionByQuarter();
    List<PositionYear> getPositionByYear();
    List<SuperContributionsYear> getSuperByYear();
    List<SuperContributionsQuarter> getSuperByQuarter();
    List<BankInterest> getBankInterest();
}
