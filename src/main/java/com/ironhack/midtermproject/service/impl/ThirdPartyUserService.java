package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.DTO.TransferThirdToCheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.users.ThirdPartyUser;
import com.ironhack.midtermproject.repository.CheckingRepository;
import com.ironhack.midtermproject.repository.ThirdPartyUserRepository;
import com.ironhack.midtermproject.service.interf.ThirdPartyUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.text.html.Option;
import java.util.Currency;
import java.util.Optional;

@Service
public class ThirdPartyUserService implements ThirdPartyUserServiceInterface {

    @Autowired
    private ThirdPartyUserRepository thirdPartyUserRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    //creating a new third party user
    public ThirdPartyUser storeThirdPartyUser(ThirdPartyUserDTO thirdPartyUserDTO){
        ThirdPartyUser newThirdPartyUser = new ThirdPartyUser();
        newThirdPartyUser.setName(thirdPartyUserDTO.getName());
        newThirdPartyUser.setHashKey(generateHashKey().toString());
        return thirdPartyUserRepository.save(newThirdPartyUser);
    }

    //delete third party
    public void deleteThirdPartyUser(Integer id){
        Optional <ThirdPartyUser> foundThirdPartyUser = thirdPartyUserRepository.findById(id);
        if(foundThirdPartyUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Party User was not found");
        } else {
            thirdPartyUserRepository.delete(foundThirdPartyUser.get());
        }
    }

    //method for generating a hashKey for the third party
    public static byte[] generateHashKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey.getEncoded();
    }

    public void thirdPartyTransferToChecking(TransferThirdToCheckingDTO transferThirdToCheckingDTO){
        //checking if the account exists
        System.out.println(transferThirdToCheckingDTO.toString());
        Checking checkingFromDB = checkingRepository.findById(transferThirdToCheckingDTO.getCheckingId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The account which you chose to do the transfer to is not found"));
        //checking if third party exists
        ThirdPartyUser thirdPartyFromDB = thirdPartyUserRepository.findByHashKey(transferThirdToCheckingDTO.getHashkey());
        if (thirdPartyFromDB == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The third party you chose to do the transfer from is not identified");
        }
        //checking if secret keys match
        String secretKeyOfCheckingFromDB = checkingFromDB.getSecretKey();
        String secretKeyProvidedByThirdParty = transferThirdToCheckingDTO.getCheckingSecretKey();
        if (secretKeyProvidedByThirdParty.equals(secretKeyOfCheckingFromDB)) {
            //checking if secondary owner matches if provided
            if (transferThirdToCheckingDTO.getSecondaryOwner() != null){
                if(checkingFromDB.getSecondaryOwner() != null){
                    String secondaryOwnerCheckingFromDB = checkingFromDB.getSecondaryOwner();
                    String secondaryOwnerProvidedByThirdParty = transferThirdToCheckingDTO.getSecondaryOwner();
                    if(secondaryOwnerCheckingFromDB.equals(secondaryOwnerProvidedByThirdParty)){
                        Money transfer = transferThirdToCheckingDTO.getTransfer();
                        if(!transfer.getCurrency().equals(Currency.getInstance("USD"))){
                            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transfer was not done. Only USD currency is accepted");
                        }
                        Money currentCheckingBalance = checkingFromDB.getBalance();
                        Money newBalance = new Money(currentCheckingBalance.increaseAmount(transfer));
                        checkingFromDB.setBalance(newBalance);
                        checkingRepository.save(checkingFromDB);
                    } else {
                        //in case the secondary owners do not match
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The operation is not allowed, the secondary owner you provided does not match with the secondary owner of account");
                    }
                } else {
                    //in case the checking account does not have the secondary owner
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The operation is not allowed, the checking account you provided does not have a secondary owner");
                }
            //checking if primary owner matches if the secondary was not provided
            } else {
                String primaryOwnerOfCheckingFromDB = checkingFromDB.getPrimaryOwner();
                String primaryOwnerProvidedByThirdParty = transferThirdToCheckingDTO.getPrimaryOwner();
                if (primaryOwnerOfCheckingFromDB.equals(primaryOwnerProvidedByThirdParty)) {
                    Money transfer = transferThirdToCheckingDTO.getTransfer();
                    if(!transfer.getCurrency().equals(Currency.getInstance("USD"))){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transfer was not done. Only USD currency is accepted");
                    }
                    Money currentCheckingBalance = checkingFromDB.getBalance();
                    Money newBalance = new Money(currentCheckingBalance.increaseAmount(transfer));
                    checkingFromDB.setBalance(newBalance);
                    checkingRepository.save(checkingFromDB);
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The operation is not allowed, the primary owner you provided does not match with the primary owner of account");
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The operation is not allowed, the secret key you provided does not match with the secret key of account");
        }
    }


}
