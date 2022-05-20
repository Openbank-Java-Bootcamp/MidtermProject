package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.users.AccountHolder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardDTO {

    private Money balance;

    private String primaryOwner;
    private String secondaryOwner;
    private Integer accountHolderId;
    private Money creditLimit;
    private BigDecimal interestRate;
    private Money penaltyFee;
    private LocalDate creationDate;


    //Constructor with secondary owner
    public CreditCardDTO(Money balance, String primaryOwner, String secondaryOwner, Integer accountHolderId, Money creditLimit, BigDecimal interestRate, LocalDate creationDate) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.accountHolderId = accountHolderId;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.creationDate = creationDate;
    }


    //Constructor without secondary owner
    public CreditCardDTO(Money balance, String primaryOwner, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.accountHolderId = accountHolderId;
    }


    //Empty Constructor
    public CreditCardDTO() {
    }


    //Getters and setters
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

    public Integer getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Integer accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }
}
