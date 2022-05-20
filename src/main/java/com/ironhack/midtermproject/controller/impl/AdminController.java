package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.AdminControllerInterface;
import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.DTO.SavingsDTO;
import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.ThirdPartyUser;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.StudentCheckingRepository;
import com.ironhack.midtermproject.service.interf.*;
import jakarta.validation.Valid;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController implements AdminControllerInterface {

    @Autowired
    private AdminServiceInterface adminServiceInterface;

    @Autowired
    private StudentCheckingServiceInterface studentCheckingServiceInterface;

    @Autowired
    private CheckingServiceInterface checkingServiceInterface;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardServiceInterface creditCardServiceInterface;

    @Autowired
    private SavingsServiceInterface savingsServiceInterface;

    @Autowired
    private ThirdPartyUserServiceInterface thirdPartyUserServiceInterface;

    //create Checking or StudentChecking
    @PostMapping("/saveaccount")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccountChecking(@RequestBody @Valid CheckingDTO checkingDTO){
        Integer accountHolderId = checkingDTO.getAccountHolderId();
        if(accountHolderRepository.findById(accountHolderId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account holder was not found, the account could not be created");
        }
        LocalDate accountHolderBirthDate = accountHolderRepository.findById(accountHolderId).get().getDateOfBirth();
        Integer year = LocalDate.parse(accountHolderBirthDate.toString()).getYear();
        LocalDate now = LocalDate.now();
        Integer yearNow = LocalDate.parse(now.toString()).getYear();
        if(yearNow - year < 18){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account was not created, account holder must be older than 18");
        } else if(yearNow - year >= 18 && yearNow - year < 24){
            studentCheckingServiceInterface.saveStudentChecking(checkingDTO);
            System.out.println("stud check created");
        } else {
            checkingServiceInterface.saveChecking(checkingDTO);
            System.out.println("check created");
        }
    }

    //create CreditCard
    @PostMapping("/savecreditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO){
        return creditCardServiceInterface.storeCreditCard(creditCardDTO);
    }

    //create Savings
    @PostMapping("/savesavings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings saveSavings(@RequestBody @Valid SavingsDTO savingsDTO){
        return savingsServiceInterface.storeSavings(savingsDTO);
    }

    //create ThirdPartyUser
    @PostMapping("/savethirdpartyuser")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyUser saveThirdPartyUser(@RequestBody @Valid ThirdPartyUserDTO thirdPartyUserDTO){
        return thirdPartyUserServiceInterface.storeThirdPartyUser(thirdPartyUserDTO);
    }

    //delete ThirdPartyUser
    @DeleteMapping("/deletethirdparty/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Integer id){
        thirdPartyUserServiceInterface.deleteThirdPartyUser(id);
    }

    //get all balances
    @GetMapping("/balances")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAllBalances(){
        return adminServiceInterface.findAllBalances();
    }
}
