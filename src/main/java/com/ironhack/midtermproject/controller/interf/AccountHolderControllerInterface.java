package com.ironhack.midtermproject.controller.interf;

import com.ironhack.midtermproject.model.DTO.TransferCheckingToCheckingDTO;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToThirdDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountHolderControllerInterface {

    List<Object[]> getAllBalances();

    void transferCheckingToChecking(TransferCheckingToCheckingDTO transferCheckingToCheckingDTO);

    void transferCheckingToThird(TransferCheckingToThirdDTO transferCheckingToThirdDTO);

}
