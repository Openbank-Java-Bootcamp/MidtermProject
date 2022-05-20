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
        Savings newSavings = new Savings();
        newSavings.setBalance(savingsDTO.getBalance());
        newSavings.setPrimaryOwner(savingsDTO.getPrimaryOwner());
        if(savingsDTO.getSecondaryOwner() != null){
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
            newSavings.setInterestRate(new Money(new BigDecimal(0.0025)));
        } else if (savingsDTO.getInterestRate().getAmount().compareTo(new BigDecimal(0.5)) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The interest rate is too big");
        } else {
            newSavings.setInterestRate(savingsDTO.getInterestRate());
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

    //Savings annual update
    public void annualSavingsUpdate(Integer id){
        //checking if Savings account exists
        Savings savingsFromDB = savingsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found"));
        Money currentSavingsBalance = savingsFromDB.getBalance();
        Money interestRate = savingsFromDB.getInterestRate();
        LocalDateTime lastTimeChecked = savingsFromDB.getLastInterestCheck();
        LocalDateTime lastTimeCheckedPlusYear = lastTimeChecked.plusYears((long) 1);
        LocalDateTime timeNow = LocalDateTime.now();
        //checking if one year already passed
        if(lastTimeCheckedPlusYear.isBefore(timeNow)){
            BigDecimal amountToAdd = currentSavingsBalance.getAmount().multiply(interestRate.getAmount()).setScale(2, RoundingMode.HALF_EVEN);
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
