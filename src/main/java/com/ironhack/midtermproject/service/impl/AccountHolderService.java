package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.DTO.TransferCheckingToCheckingDTO;
import com.ironhack.midtermproject.model.DTO.TransferCheckingToThirdDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.CheckingRepository;
import com.ironhack.midtermproject.repository.SavingsRepository;
import com.ironhack.midtermproject.repository.StudentCheckingRepository;
import com.ironhack.midtermproject.service.interf.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Service
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    public void transferCheckingToChecking(TransferCheckingToCheckingDTO transferCheckingToCheckingDTO){
        //checking if both checking accounts exist
        Checking checkingFromDBFrom = checkingRepository.findById(transferCheckingToCheckingDTO.getAccountIdFrom()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The account which you chose for doing a transfer from is not found"));
        Checking checkingFromDBTo = checkingRepository.findById(transferCheckingToCheckingDTO.getAccountIdTo()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you chose to do the transfer for is not found"));
        //checking if the account the transfer is going from belongs to the user
        String username = getLoggedInUsername();
        Integer accountHolderId = accountHolderRepository.findIdByUsername(username);
        List<Integer> accountsList = accountHolderRepository.findAccountIdByAccountHolderId(accountHolderId);
        Integer accountsId = transferCheckingToCheckingDTO.getAccountIdFrom();
        if(accountsList.contains(accountsId)){
            Money transferAmount = transferCheckingToCheckingDTO.getTransfer();
            if(transferAmount.getAmount().compareTo(new BigDecimal(0)) < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The transfer amount needs to be more than 0");
            }
            if(!transferAmount.getCurrency().equals(Currency.getInstance("USD"))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer was not done. Only USD currency is accepted");
            }
            Money currentBalanceFrom = checkingFromDBFrom.getBalance();
            Money currentBalanceTo = checkingFromDBTo.getBalance();
            Money minimumBalanceFrom = checkingFromDBFrom.getMinimumBalance();
            Money balanceAfterTransferFrom = new Money(currentBalanceFrom.decreaseAmount(transferAmount));
            //if balance after transfer is more than minimumBalance
            if(balanceAfterTransferFrom.getAmount().compareTo(minimumBalanceFrom.getAmount()) > 0){
                Money balanceAfterTransferTo = new Money(currentBalanceTo.increaseAmount(transferAmount));
                checkingFromDBTo.setBalance(balanceAfterTransferTo);
                checkingFromDBFrom.setBalance(balanceAfterTransferFrom);
                checkingRepository.save(checkingFromDBTo);
                checkingRepository.save(checkingFromDBFrom);
            //if balance after transfer is less than the minimum
            } else if (balanceAfterTransferFrom.getAmount().compareTo(minimumBalanceFrom.getAmount()) < 0){
                //checking if it's more than 40 so the transfer can be made and the penalty can be applied
                if(balanceAfterTransferFrom.getAmount().compareTo(new BigDecimal(40)) > 0){
                    Money balanceAfterTransferTo = new Money(currentBalanceTo.increaseAmount(transferAmount));
                    checkingFromDBTo.setBalance(balanceAfterTransferTo);
                    Money balanceAfterTransferAndPenalty = new Money(balanceAfterTransferFrom.decreaseAmount(new Money(new BigDecimal(40))));
                    checkingFromDBFrom.setBalance(balanceAfterTransferAndPenalty);
                    checkingRepository.save(checkingFromDBTo);
                    checkingRepository.save(checkingFromDBFrom);
                } else {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transfer was not done. Not Enough funds");
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not do transfers from accounts which don't belong to you");
        }
    }


    //transfer from Checking to Third Party User
    public void transferCheckingToThird(TransferCheckingToThirdDTO transferCheckingToThirdDTO){
        //checking if checking accounts exists
        Checking checkingFromDBFrom = checkingRepository.findById(transferCheckingToThirdDTO.getAccountIdFrom()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The account which you chose for doing a transfer from is not found"));
        //checking if the account the transfer is going from belongs to the user
        String username = getLoggedInUsername();
        Integer accountHolderId = accountHolderRepository.findIdByUsername(username);
        List<Integer> accountsList = accountHolderRepository.findAccountIdByAccountHolderId(accountHolderId);
        Integer accountsId = transferCheckingToThirdDTO.getAccountIdFrom();
        if(accountsList.contains(accountsId)){
            Money transferAmount = transferCheckingToThirdDTO.getTransfer();
            if(transferAmount.getAmount().compareTo(new BigDecimal(0)) < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The transfer amount needs to be more than 0");
            }
            //checking if currency is USD
            if(!transferAmount.getCurrency().equals(Currency.getInstance("USD"))){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transfer was not done. Only USD currency is accepted");
            }
            Money currentBalanceFrom = checkingFromDBFrom.getBalance();
            Money minimumBalanceFrom = checkingFromDBFrom.getMinimumBalance();
            Money balanceAfterTransferFrom = new Money(currentBalanceFrom.decreaseAmount(transferAmount));
            //if balance after transfer is more than minimumBalance
            if(balanceAfterTransferFrom.getAmount().compareTo(minimumBalanceFrom.getAmount()) > 0){
                checkingFromDBFrom.setBalance(balanceAfterTransferFrom);
                checkingRepository.save(checkingFromDBFrom);
            //if balance after transfer is less than the minimum
            } else if (balanceAfterTransferFrom.getAmount().compareTo(minimumBalanceFrom.getAmount()) < 0){
                //checking if it's more than 40 so the transfer can be made and the penalty can be applied
                if(balanceAfterTransferFrom.getAmount().compareTo(new BigDecimal(40)) > 0){
                    Money balanceAfterTransferAndPenalty = new Money(balanceAfterTransferFrom.decreaseAmount(new Money(new BigDecimal(40))));
                    checkingFromDBFrom.setBalance(balanceAfterTransferAndPenalty);
                    checkingRepository.save(checkingFromDBFrom);
                } else {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transfer was not done. Not Enough funds");
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not do transfers from accounts which don't belong to you");
        }
    }

    //find balances of logged in accountHolder
    public List<Object[]> findAllBalancesByUsername(){
        String username = getLoggedInUsername();
        Integer accountHolderId = accountHolderRepository.findIdByUsername(username);
        List<Integer> accountId = accountHolderRepository.findAccountIdByAccountHolderId(accountHolderId);
        return accountHolderRepository.findBalancesByAccountId(accountId);
    }


    //for getting the logged in account's username
    public String getLoggedInUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }


}
