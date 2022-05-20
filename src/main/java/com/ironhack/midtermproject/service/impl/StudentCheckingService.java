package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.Status;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.StudentCheckingRepository;
import com.ironhack.midtermproject.service.interf.StudentCheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;

@Service
public class StudentCheckingService implements StudentCheckingServiceInterface {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //create new Student Checking account
    public StudentChecking saveStudentChecking(CheckingDTO checkingDTO){
        Integer accountHolderId = checkingDTO.getAccountHolderId();
        StudentChecking newStudentChecking = new StudentChecking();
        newStudentChecking.setBalance(checkingDTO.getBalance());
        newStudentChecking.setPrimaryOwner(checkingDTO.getPrimaryOwner());
        if(checkingDTO.getSecondaryOwner() != null){
            newStudentChecking.setSecondaryOwner(checkingDTO.getSecondaryOwner());
        }
        newStudentChecking.setSecretKey(generateSecretKey().toString());
        newStudentChecking.setAccountHolder(accountHolderRepository.findById(accountHolderId).get());
        newStudentChecking.setCreationDate(LocalDateTime.now());
        newStudentChecking.setStatus(Status.ACTIVE);
        return studentCheckingRepository.save(newStudentChecking);
    }


    //for generating a SecretKey in saveStudentChecking method
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
