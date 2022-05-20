package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.SavingsDTO;
import com.ironhack.midtermproject.model.accounts.Savings;

public interface SavingsServiceInterface {

    Savings storeSavings(SavingsDTO savingsDTO);

    void annualSavingsUpdate(Integer id);

}
