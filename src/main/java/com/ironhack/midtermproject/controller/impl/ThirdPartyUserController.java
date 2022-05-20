package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.ThirdPartyUserControllerInterface;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToCheckingDTO;
import com.ironhack.midtermproject.model.DTO.TransferThirdToCheckingDTO;
import com.ironhack.midtermproject.service.interf.ThirdPartyUserServiceInterface;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/thirdpartyusers/")
public class ThirdPartyUserController implements ThirdPartyUserControllerInterface {

    @Autowired
    private ThirdPartyUserServiceInterface thirdPartyUserServiceInterface;

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferThirdPartyToChecking(@RequestHeader String hashkey, @RequestBody @Valid TransferThirdToCheckingDTO transferThirdToCheckingDTO){
        thirdPartyUserServiceInterface.thirdPartyTransferToChecking(transferThirdToCheckingDTO);
    }


}
