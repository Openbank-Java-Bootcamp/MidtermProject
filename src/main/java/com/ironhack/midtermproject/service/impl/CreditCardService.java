package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.CreditCardRepository;
import com.ironhack.midtermproject.service.interf.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardService implements CreditCardServiceInterface {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //create new CreditCard
    public CreditCard storeCreditCard(CreditCardDTO creditCardDTO){
        Integer accountHolderId = creditCardDTO.getAccountHolderId();
        if(accountHolderRepository.findById(accountHolderId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account holder was not found, the account could not be created");
        }
        if(creditCardDTO.getBalance().getAmount().compareTo(new BigDecimal(0)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The credit card was not created. The balance needs to be more than 0");
        }
        if(!creditCardDTO.getBalance().getCurrency().equals(Currency.getInstance("USD"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created. Only USD balance is accepted");
        }
        CreditCard newCreditCard = new CreditCard();
        newCreditCard.setBalance(creditCardDTO.getBalance());
        verifyName(creditCardDTO.getPrimaryOwner());
        newCreditCard.setPrimaryOwner(creditCardDTO.getPrimaryOwner());
        if(creditCardDTO.getSecondaryOwner() != null){
            verifyName(creditCardDTO.getSecondaryOwner());
            newCreditCard.setSecondaryOwner(creditCardDTO.getSecondaryOwner());
        }
        newCreditCard.setAccountHolder1(accountHolderRepository.findById(accountHolderId).get());
        if (creditCardDTO.getCreditLimit().getAmount() != null){
            if (creditCardDTO.getCreditLimit().getAmount().compareTo(new BigDecimal(100)) > 0 && creditCardDTO.getCreditLimit().getAmount().compareTo(new BigDecimal(100000)) < 0){
                newCreditCard.setCreditLimit(creditCardDTO.getCreditLimit());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The credit limit is either too small or too big");
            }
        } else {
            newCreditCard.setCreditLimit(new Money(new BigDecimal(100)));
        }
        if (creditCardDTO.getInterestRate() == null){
            newCreditCard.setInterestRate(new BigDecimal(0.2).setScale(4, RoundingMode.HALF_EVEN));
        } else if (creditCardDTO.getInterestRate().compareTo(new BigDecimal(0.1)) > 0 && creditCardDTO.getInterestRate().compareTo(new BigDecimal(0.2)) < 0){
            newCreditCard.setInterestRate(creditCardDTO.getInterestRate().setScale(4, RoundingMode.HALF_EVEN));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The interest rate is either too small or too big");
        }
        newCreditCard.setCreationDate(LocalDate.now());
        newCreditCard.setLastInterestCheck(LocalDateTime.now());
        return creditCardRepository.save(newCreditCard);
    }

    //CreditCard monthly update
    public void monthlyCreditCartUpdate(Integer id){
        //checking if Savings account exists
        CreditCard creditCardFromDB = creditCardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CreditCard not found"));
        Money currentCreditCardBalance = creditCardFromDB.getBalance();
        BigDecimal interestRate = creditCardFromDB.getInterestRate().setScale(1, RoundingMode.HALF_EVEN);
        LocalDateTime lastTimeChecked = creditCardFromDB.getLastInterestCheck();
        LocalDateTime lastTimeCheckedPlusOneMonth = lastTimeChecked.plusMonths((long) 1);
        LocalDateTime timeNow = LocalDateTime.now();
        //checking if one month already passed
        if(lastTimeCheckedPlusOneMonth.isBefore(timeNow)){
            if(interestRate.compareTo(new BigDecimal(0.1)) == 0){
                BigDecimal amountToAdd = currentCreditCardBalance.getAmount().multiply(new BigDecimal(0.01));
                Money newBalance = new Money(currentCreditCardBalance.increaseAmount(amountToAdd));
                creditCardFromDB.setBalance(newBalance);
                creditCardFromDB.setLastInterestCheck(timeNow);
                creditCardRepository.save(creditCardFromDB);
            } else {
                BigDecimal amountToAdd = currentCreditCardBalance.getAmount().multiply(new BigDecimal(0.02));
                Money newBalance = new Money(currentCreditCardBalance.increaseAmount(amountToAdd));
                creditCardFromDB.setBalance(newBalance);
                creditCardFromDB.setLastInterestCheck(timeNow);
                creditCardRepository.save(creditCardFromDB);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "The balance was not upgraded because less than one month passed till the interest rate was added");
        }
    }

    //for checking if primaryOwner and secondaryOwner names contain letters only when creating a credit card
    public void verifyName(String name) {
        String regx = "^[a-zA-Z][a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Only letters and spaces allowed in primaryOwner and secondaryOwner field, please check the information you passed in these fields");
        }
    }









}
