package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.finance.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FinanceMapper {

    @Select("select * from financial_position_quarter;")
    List<PositionQuarter> getPositionByQuarter();

    @Select("select * from financial_position_year;")
    List<PositionYear> getPositionByYear();

    @Select("select * from super_contributions_year;")
    List<SuperContributionsYear> getSuperByYear();

    @Select("select * from super_contributions_quarter;")
    List<SuperContributionsQuarter> getSuperByQuarter();

    @Select("select * from bank_interest;")
    List<BankInterest> getBankInterest();
}
