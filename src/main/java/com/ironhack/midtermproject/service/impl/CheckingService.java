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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CheckingService implements CheckingServiceInterface {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //create new Checking account
    public Checking saveChecking(CheckingDTO checkingDTO){
        Integer accountHolderId = checkingDTO.getAccountHolderId();
        Checking newChecking = new Checking();
        newChecking.setBalance(checkingDTO.getBalance());
        System.out.println(checkingDTO.getBalance().getAmount());
        newChecking.setPrimaryOwner(checkingDTO.getPrimaryOwner());
        if(checkingDTO.getSecondaryOwner() != null){
            newChecking.setSecondaryOwner(checkingDTO.getSecondaryOwner());
        }
        newChecking.setSecretKey(generateSecretKey().toString());
        newChecking.setAccountHolder(accountHolderRepository.findById(accountHolderId).get());
        newChecking.setCreationDate(LocalDateTime.now());
        newChecking.setStatus(Status.ACTIVE);
        return checkingRepository.save(newChecking);
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
