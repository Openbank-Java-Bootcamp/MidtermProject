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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentCheckingService implements StudentCheckingServiceInterface {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //create new Student Checking account
    public StudentChecking saveStudentChecking(CheckingDTO checkingDTO){
        Integer accountHolderId = checkingDTO.getAccountHolderId();
        if(checkingDTO.getBalance().getAmount().compareTo(new BigDecimal(0)) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created. The balance needs to be more than 0");
        }
        if(!checkingDTO.getBalance().getCurrency().equals(Currency.getInstance("USD"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created. Only USD balance is accepted");
        }
        StudentChecking newStudentChecking = new StudentChecking();
        newStudentChecking.setBalance(checkingDTO.getBalance());
        verifyName(checkingDTO.getPrimaryOwner());
        newStudentChecking.setPrimaryOwner(checkingDTO.getPrimaryOwner());
        if(checkingDTO.getSecondaryOwner() != null){
            verifyName(checkingDTO.getSecondaryOwner());
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


    //for checking if primaryOwner and secondaryOwner names contain letters only when creating a student checking account
    public void verifyName(String name) {
        String regx = "^[a-zA-Z][a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Only letters and spaces allowed in primaryOwner and secondaryOwner field, please check the information you passed in these fields");
        }
    }

}
