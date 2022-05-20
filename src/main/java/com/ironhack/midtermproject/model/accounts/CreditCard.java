package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer creditCardId;

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount"))
    })
    @Embedded
    @NotNull
    private Money balance;

    @Column(name = "primary_owner")
    @NotNull
    private String primaryOwner;

    @Column(name = "secondary_owner")
    private String secondaryOwner;

    private LocalDateTime lastInterestCheck;

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount"))
    })
    @Embedded
    private Money creditLimit;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))
    })
    @Embedded
    private final Money penaltyFee = new Money(new BigDecimal(40));;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinTable(name = "credit_card_account_holder",
            joinColumns = {@JoinColumn(name = "credit_card_id")},
            inverseJoinColumns = {@JoinColumn(name = "AccountHolder_id")})
    @NotNull
    private AccountHolder accountHolder1;


    //Constructor with secondaryOwner
    public CreditCard(Money balance, String primaryOwner, String secondaryOwner, AccountHolder accountHolder1) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = LocalDate.now();
        this.accountHolder1 = accountHolder1;
    }

    //Constructor without secondaryOwner
    public CreditCard(Money balance, String primaryOwner, AccountHolder accountHolder1) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationDate = LocalDate.now();
        this.accountHolder1 = accountHolder1;
    }

    //Empty constructor
    public CreditCard() {
    }


    //Getters and setters
    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
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


    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AccountHolder getAccountHolder1() {
        return accountHolder1;
    }

    public void setAccountHolder1(AccountHolder accountHolder1) {
        this.accountHolder1 = accountHolder1;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastInterestCheck() {
        return lastInterestCheck;
    }

    public void setLastInterestCheck(LocalDateTime lastInterestCheck) {
        this.lastInterestCheck = lastInterestCheck;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }
}
