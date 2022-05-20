package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.CreditCardControllerInterface;
import com.ironhack.midtermproject.service.interf.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creditcards")
public class CreditCard implements CreditCardControllerInterface {

    @Autowired
    private CreditCardServiceInterface creditCardServiceInterface;


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditCardMonthlyUpdate(@PathVariable Integer id){
        creditCardServiceInterface.monthlyCreditCartUpdate(id);
    }
}
