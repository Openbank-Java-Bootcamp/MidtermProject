package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

@Entity(name = "checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))
    })
    @Embedded
    @NotNull
    private final Money monthlyMaintenanceFee = new Money(new BigDecimal(12));

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    @Embedded
    @NotNull
    private final Money minimumBalance = new Money(new BigDecimal(250));


    //Constructor containing secondaryOwner
    public Checking(Money balance, String primaryOwner, String secondaryOwner, AccountHolder accountHolder) {
        super(balance, primaryOwner, secondaryOwner, accountHolder);
    }

    //Constructor that does not contain a secondaryOwner
    public Checking(Money balance, String primaryOwner, AccountHolder accountHolder) {
        super(balance, primaryOwner, accountHolder);
    }

    //Empty constructor
    public Checking() {
    }


    //Getters and Setters
    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }


    public Money getMinimumBalance() {
        return minimumBalance;
    }


}
