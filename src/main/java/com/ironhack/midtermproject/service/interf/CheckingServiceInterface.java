package com.ironhack.midtermproject.service.interf;


import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;

public interface CheckingServiceInterface {

    Checking saveChecking(CheckingDTO checkingDTO);

}
