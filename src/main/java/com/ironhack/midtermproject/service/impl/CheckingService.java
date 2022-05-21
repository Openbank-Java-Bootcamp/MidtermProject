package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.accounts.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.CheckingRepository;
import com.ironhack.midtermproject.service.interf.CheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckingService implements CheckingServiceInterface {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //create new Checking account
    public Checking saveChecking(CheckingDTO checkingDTO){
        Integer accountHolderId = checkingDTO.getAccountHolderId();
        if(checkingDTO.getBalance().getAmount().compareTo(new BigDecimal(0)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created. The balance needs to be more than 0");
        }
        if(!checkingDTO.getBalance().getCurrency().equals(Currency.getInstance("USD"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created. Only USD balance is accepted");
        }
        verifyName(checkingDTO.getPrimaryOwner());
        Checking newChecking = new Checking();
        newChecking.setBalance(checkingDTO.getBalance());
        newChecking.setPrimaryOwner(checkingDTO.getPrimaryOwner());
        if(checkingDTO.getSecondaryOwner() != null){
            verifyName(checkingDTO.getSecondaryOwner());
            newChecking.setSecondaryOwner(checkingDTO.getSecondaryOwner());
        }
        newChecking.setSecretKey(generateSecretKey().toString());
        newChecking.setAccountHolder(accountHolderRepository.findById(accountHolderId).get());
        newChecking.setCreationDate(LocalDateTime.now());
        newChecking.setStatus(Status.ACTIVE);
        return checkingRepository.save(newChecking);
    }


    //for checking if primaryOwner and secondaryOwner names contain letters only when creating a checking account
    public void verifyName(String name) {
        String regx = "^[a-zA-Z][a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Only letters and spaces allowed in primaryOwner and secondaryOwner field, please check the information you passed in these fields");
        }
    }

    //for generating a SecretKey in saveChecking method
    public static byte[] generateSecretKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey.getEncoded();
    }

}
