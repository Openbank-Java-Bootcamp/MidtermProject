package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer accountId;

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

    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_amount"))
    })
    @Embedded
    @NotNull
    private final Money penaltyFee = new Money(new BigDecimal(40));

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "secret_key")
    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne
    @JoinTable(name = "account_and_account_holder",
    joinColumns = {@JoinColumn(name = "accounts_id")},
    inverseJoinColumns = {@JoinColumn(name = "AccountHolder_id")})
    private AccountHolder accountHolder;


    //Constructor with secondaryOwner
    public Account(Money balance, String primaryOwner, String secondaryOwner, AccountHolder accountHolder) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.secretKey = generateSecretKey().toString();
        this.creationDate = LocalDateTime.now();
        this.accountHolder = accountHolder;
        this.status = Status.ACTIVE;
    }

    //Constructor without secondaryOwner
    public Account(Money balance, String primaryOwner, AccountHolder accountHolder) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secretKey = generateSecretKey().toString();
        this.creationDate = LocalDateTime.now();
        this.accountHolder = accountHolder;
        this.status = Status.ACTIVE;
    }

    public Account() {
    }


    //a method for automatic generation of the secretKey
    public static byte[] generateSecretKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey.getEncoded();
    }


    //Setters and Getters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public Money getPenaltyFee() {
        return penaltyFee;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
