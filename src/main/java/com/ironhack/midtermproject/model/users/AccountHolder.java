package com.ironhack.midtermproject.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.model.accounts.Account;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "account_holder")
public class AccountHolder extends User{

    @Column(name = "date_of_birth")
    @NotNull
    private LocalDate dateOfBirth;

    @Embedded
    @NotNull
    private Address primaryAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "mailing_state"))
    })
    @Embedded
    private Address mailingAddress;

    @OneToMany(mappedBy = "accountHolder")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Account> accountsSet;

    @OneToMany(mappedBy = "accountHolder1")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<CreditCard> creditCardSet;


    //Constructor with mailing address
    public AccountHolder(String name, String password, String username, Role role, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress, Set<Account> accountsSet, Set<CreditCard> creditCardSet) {
        super(name, password, username, role);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accountsSet = accountsSet;
        this.creditCardSet = creditCardSet;
    }

    //Constructor without mailing address
    public AccountHolder(String name, String password, String username, Role role, LocalDate dateOfBirth, Address primaryAddress, Set<Account> accountsSet, Set<CreditCard> creditCardSet) {
        super(name, password, username, role);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.accountsSet = accountsSet;
        this.creditCardSet = creditCardSet;
    }

    //Empty constructor
    public AccountHolder() {
    }

    //Constructor without creditCardSet and without accountSet and creditCardSet and mailing address
    public AccountHolder(String name, String password, String username, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, password, username);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String password, String username, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, password, username);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    //Getters and Setters
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Set<Account> getAccountsSet() {
        return accountsSet;
    }

    public void setAccountsSet(Set<Account> accountsSet) {
        this.accountsSet = accountsSet;
    }

    public Set<CreditCard> getCreditCardSet() {
        return creditCardSet;
    }

    public void setCreditCardSet(Set<CreditCard> creditCardSet) {
        this.creditCardSet = creditCardSet;
    }




}
