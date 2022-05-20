package com.ironhack.midtermproject.model.DTO;

import com.ironhack.midtermproject.model.accounts.Money;
import jakarta.validation.constraints.Positive;

public class TransferThirdToCheckingDTO {

    private String hashkey;

    private Integer checkingId;

    private String primaryOwner;

    private String secondaryOwner;

    private String checkingSecretKey;


    private Money transfer;

    public TransferThirdToCheckingDTO(String hashkey, Integer checkingId, String primaryOwner, String secondaryOwner, String checkingSecretKey, Money transfer) {
        this.hashkey = hashkey;
        this.checkingId = checkingId;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.checkingSecretKey = checkingSecretKey;
        this.transfer = transfer;
    }

    public TransferThirdToCheckingDTO(String hashkey, Integer checkingId, String primaryOwner, String checkingSecretKey, Money transfer) {
        this.hashkey = hashkey;
        this.checkingId = checkingId;
        this.primaryOwner = primaryOwner;
        this.checkingSecretKey = checkingSecretKey;
        this.transfer = transfer;
    }

    public TransferThirdToCheckingDTO() {
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public Integer getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(Integer checkingId) {
        this.checkingId = checkingId;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getCheckingSecretKey() {
        return checkingSecretKey;
    }

    public void setCheckingSecretKey(String checkingSecretKey) {
        this.checkingSecretKey = checkingSecretKey;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getTransfer() {
        return transfer;
    }

    public void setTransfer(Money transfer) {
        this.transfer = transfer;
    }
}
