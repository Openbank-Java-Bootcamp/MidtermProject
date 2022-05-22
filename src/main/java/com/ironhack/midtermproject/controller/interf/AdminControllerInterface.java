package com.ironhack.midtermproject.controller.interf;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.DTO.SavingsDTO;
import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.users.ThirdPartyUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminControllerInterface {

    void saveAccountChecking(CheckingDTO checkingDTO);

    CreditCard saveCreditCard(CreditCardDTO creditCardDTO);

    Savings saveSavings(SavingsDTO savingsDTO);

    ThirdPartyUser saveThirdPartyUser(ThirdPartyUserDTO thirdPartyUserDTO);

    List<Object[]> getAllBalances();

    void deleteThird(Integer id);
}
