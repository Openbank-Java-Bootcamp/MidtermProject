package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;

public class TransferCheckingToThirdDTO {

    private String hashkey;

    private Money transfer;

    private Integer accountIdFrom;

    public TransferCheckingToThirdDTO(String hashkey, Money transfer, Integer accountIdFrom) {
        this.hashkey = hashkey;
        this.transfer = transfer;
        this.accountIdFrom = accountIdFrom;
    }

    public TransferCheckingToThirdDTO() {
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public Money getTransfer() {
        return transfer;
    }

    public void setTransfer(Money transfer) {
        this.transfer = transfer;
    }

    public Integer getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Integer accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    @Override
    public String toString() {
        return "TransferCheckingToThirdDTO{" +
                "hashkey='" + hashkey + '\'' +
                ", transfer=" + transfer +
                ", accountIdFrom=" + accountIdFrom +
                '}';
    }
}
