package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "savings")
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account {

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    @Embedded
    private Money minimumBalance;

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "interest_rate_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "interest_rate_amount"))
    })
    @Embedded
    private Money interestRate;

    private LocalDateTime lastInterestCheck;

    //Constructor with secondaryOwner
    public Savings(Money balance, String primaryOwner, String secondaryOwner, AccountHolder accountHolder) {
        super(balance, primaryOwner, secondaryOwner, accountHolder);
        this.minimumBalance = new Money(new BigDecimal(1000));
        this.interestRate = new Money(new BigDecimal(0.0025));

    }



    //Constructor without secondaryOwner
    public Savings(Money balance, String primaryOwner, AccountHolder accountHolder, Money minimumBalance, Money interestRate) {
        super(balance, primaryOwner, accountHolder);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    //Empty constructor
    public Savings() {
    }


    //Getters and Setters
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

    public LocalDateTime getLastInterestCheck() {
        return lastInterestCheck;
    }

    public void setLastInterestCheck(LocalDateTime lastInterestCheck) {
        this.lastInterestCheck = lastInterestCheck;
    }
}
