package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.TransferCheckingToCheckingDTO;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToThirdDTO;

import java.util.List;

public interface AccountHolderServiceInterface {

    List<Object[]> findAllBalancesByUsername();

    void transferCheckingToChecking(TransferCheckingToCheckingDTO transferCheckingToCheckingDTO);

    void transferCheckingToThird(TransferCheckingToThirdDTO transferCheckingToThirdDTO);

}
