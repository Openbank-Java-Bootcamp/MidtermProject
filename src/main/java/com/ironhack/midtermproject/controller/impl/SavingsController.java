package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.SavingsControllerInterface;
import com.ironhack.midtermproject.service.interf.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/savings")
public class SavingsController implements SavingsControllerInterface {

    @Autowired
    private SavingsServiceInterface savingsServiceInterface;

    //Annual savings update
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savingsAnnualUpdate(@PathVariable Integer id){
        savingsServiceInterface.annualSavingsUpdate(id);
    }
}
