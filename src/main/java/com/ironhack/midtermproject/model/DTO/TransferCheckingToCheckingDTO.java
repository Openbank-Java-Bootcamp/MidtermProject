package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;

public class TransferCheckingToCheckingDTO {

    private Integer accountIdFrom;

    private Integer accountIdTo;

    private String primaryOwner;

    private Money transfer;

    public TransferCheckingToCheckingDTO(Integer accountIdFrom, Integer accountIdTo, String primaryOwner, Money transfer) {
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.primaryOwner = primaryOwner;
        this.transfer = transfer;
    }

    public TransferCheckingToCheckingDTO() {
    }

    public Integer getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Integer accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public Integer getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(Integer accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public Money getTransfer() {
        return transfer;
    }

    public void setTransfer(Money transfer) {
        this.transfer = transfer;
    }
}
