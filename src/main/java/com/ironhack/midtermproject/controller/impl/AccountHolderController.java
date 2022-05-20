package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.AccountHolderControllerInterface;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToCheckingDTO;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToThirdDTO;
import com.ironhack.midtermproject.service.interf.AccountHolderServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountholders/")
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    private AccountHolderServiceInterface accountHolderServiceInterface;

    @GetMapping("/balances")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAllBalances(){
        return accountHolderServiceInterface.findAllBalancesByUsername();
    }

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferCheckingToChecking(@RequestBody @Valid TransferCheckingToCheckingDTO transferCheckingToCheckingDTO){
        accountHolderServiceInterface.transferCheckingToChecking(transferCheckingToCheckingDTO);
    }

    @PatchMapping("/transfer/thirdparty")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferCheckingToThird(@RequestBody @Valid TransferCheckingToThirdDTO transferCheckingToThirdDTO){
        accountHolderServiceInterface.transferCheckingToThird(transferCheckingToThirdDTO);
    }





}
