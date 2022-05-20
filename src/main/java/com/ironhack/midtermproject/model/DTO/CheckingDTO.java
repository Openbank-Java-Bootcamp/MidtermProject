package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.accounts.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;


public class CheckingDTO {

    private Money balance;
    private String primaryOwner;
    private String secondaryOwner;

    private Integer accountHolderId;

    //Constructor with secondary owner
    public CheckingDTO(Money balance, String primaryOwner, String secondaryOwner, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.accountHolderId = accountHolderId;
    }

    //Constructor without secondary owner
    public CheckingDTO(Money balance, String primaryOwner, Integer accountHolderId) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.accountHolderId = accountHolderId;
    }

    //Empty constructor
    public CheckingDTO() {
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

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }
}
