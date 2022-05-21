package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.SavingsDTO;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.Status;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.SavingsRepository;
import com.ironhack.midtermproject.service.interf.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SavingsService implements SavingsServiceInterface {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    //save savings
    public Savings storeSavings(SavingsDTO savingsDTO){
        Integer accountHolderId = savingsDTO.getAccountHolderId();
        if(accountHolderRepository.findById(accountHolderId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account holder was not found, the account could not be created");
        }
        if(savingsDTO.getBalance().getAmount().compareTo(new BigDecimal(0)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The savings account was not created. The balance needs to be more than 0");
        }
        if(!savingsDTO.getBalance().getCurrency().equals(Currency.getInstance("USD"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The savings account was not created. Only USD balance is accepted");
        }
        Savings newSavings = new Savings();
        newSavings.setBalance(savingsDTO.getBalance());
        verifyName(savingsDTO.getPrimaryOwner());
        newSavings.setPrimaryOwner(savingsDTO.getPrimaryOwner());
        if(savingsDTO.getSecondaryOwner() != null){
            verifyName(savingsDTO.getSecondaryOwner());
            newSavings.setSecondaryOwner(savingsDTO.getSecondaryOwner());
        }
        if(savingsDTO.getMinimumBalance() == null){
            newSavings.setMinimumBalance(new Money(new BigDecimal(1000)));
        } else if (savingsDTO.getMinimumBalance().getAmount().compareTo(new BigDecimal(100)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The minimum balance is too small");
        } else {
            newSavings.setMinimumBalance(savingsDTO.getMinimumBalance());
        }
        newSavings.setAccountHolder(accountHolderRepository.findById(accountHolderId).get());
        newSavings.setCreationDate(LocalDateTime.now());
        if(savingsDTO.getInterestRate() == null){
            newSavings.setInterestRate(new BigDecimal(0.0025).setScale(4, RoundingMode.HALF_EVEN));
        } else if (savingsDTO.getInterestRate().compareTo(new BigDecimal(0.5)) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The interest rate is too big");
        } else if(savingsDTO.getInterestRate().compareTo(new BigDecimal(0)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The interest rate either is below 0");
        }else {
            newSavings.setInterestRate(savingsDTO.getInterestRate().setScale(4, RoundingMode.HALF_EVEN));
        }
        newSavings.setStatus(Status.ACTIVE);
        newSavings.setLastInterestCheck(LocalDateTime.now());
        newSavings.setSecretKey(generateSecretKey().toString());
        return savingsRepository.save(newSavings);
    }

    //for generating a SecretKey in saveSavings method
    public static byte[] generateSecretKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey.getEncoded();
    }

    //for checking if primaryOwner and secondaryOwner names contain letters only when creating a savings account
    public void verifyName(String name) {
        String regx = "^[a-zA-Z][a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Only letters and spaces allowed in primaryOwner and secondaryOwner field, please check the information you passed in these fields");
        }
    }

    //Savings annual update
    public void annualSavingsUpdate(Integer id){
        //checking if Savings account exists
        Savings savingsFromDB = savingsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found"));
        Money currentSavingsBalance = savingsFromDB.getBalance();
        BigDecimal interestRate = savingsFromDB.getInterestRate().setScale(2, RoundingMode.HALF_EVEN);
        LocalDateTime lastTimeChecked = savingsFromDB.getLastInterestCheck();
        LocalDateTime lastTimeCheckedPlusYear = lastTimeChecked.plusYears((long) 1);
        LocalDateTime timeNow = LocalDateTime.now();
        //checking if one year already passed
        if(lastTimeCheckedPlusYear.isBefore(timeNow)){
            BigDecimal amountToAdd = currentSavingsBalance.getAmount().multiply(interestRate);
            Money newBalance = new Money(currentSavingsBalance.increaseAmount(amountToAdd));
            savingsFromDB.setBalance(newBalance);
            savingsFromDB.setLastInterestCheck(LocalDateTime.now());
            savingsRepository.save(savingsFromDB);
            //if one year didn't pass after the last check
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "The balance was not upgraded because less than one year passed till the interest rate was added");
        }
    }





}
