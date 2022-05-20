package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SavingsDTO {

    private Money balance;

    @NotNull
    private String primaryOwner;

    private String secondaryOwner;

    private Money minimumBalance;

    private Money interestRate;

    @NotNull
    private Integer accountHolderId;


    //Constructor with secondary owner, minBalance and interestRate
    public SavingsDTO(Money balance, String primaryOwner, String secondaryOwner, Money minimumBalance, Money interestRate, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.accountHolderId = accountHolderId;
    }

    ////Constructor without secondary owner, but with minBalance and interestRate
    public SavingsDTO(Money balance, String primaryOwner, Money minimumBalance, Money interestRate, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.accountHolderId = accountHolderId;
    }

    ////Constructor with secondary owner, minBalance and interestRate
    public SavingsDTO(Money balance, String primaryOwner, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.accountHolderId = accountHolderId;
    }

    public SavingsDTO() {
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Money getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Money interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Integer accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
