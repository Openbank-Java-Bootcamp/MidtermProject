package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.accounts.CreditCard;

public interface CreditCardServiceInterface {

    CreditCard storeCreditCard(CreditCardDTO creditCardDTO);

    void monthlyCreditCartUpdate(Integer id);
}
