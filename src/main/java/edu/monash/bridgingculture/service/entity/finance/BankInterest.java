package edu.monash.bridgingculture.service.entity.finance;

import lombok.Data;

@Data
public class BankInterest {
    private String image;
    private String savingsAccounts;
    private String validFrom;
    private String base;
    private String maxBonus;
    private String maxTotal;
    private String maxBalance;
    private String effectiveAsOf;
    private String lastCheckedOrEdited;
    private String minimumAge;
    private String maximumAge;
    private String minimumDepositAmount;
    private String depositFrequency;
    private String bonus;
}
