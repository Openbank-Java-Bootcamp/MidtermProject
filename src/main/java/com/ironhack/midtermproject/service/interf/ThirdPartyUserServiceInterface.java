package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.DTO.TransferThirdToCheckingDTO;
import com.ironhack.midtermproject.model.users.ThirdPartyUser;

public interface ThirdPartyUserServiceInterface {

    ThirdPartyUser storeThirdPartyUser(ThirdPartyUserDTO thirdPartyUserDTO);

    void thirdPartyTransferToChecking(TransferThirdToCheckingDTO transferThirdToCheckingDTO);

    void deleteThirdPartyUser(Integer id);
}
