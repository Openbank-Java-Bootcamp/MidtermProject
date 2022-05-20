package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.Optional;

@Entity
@Table(name = "student_checking")
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {

    //Constructor with secondaryOwner
    public StudentChecking(Money balance, String primaryOwner, String secondaryOwner, AccountHolder accountHolder) {
        super(balance, primaryOwner, secondaryOwner, accountHolder);
    }

    //Constructor without secondaryOwner
    public StudentChecking(Money balance, String primaryOwner, AccountHolder accountHolder) {
        super(balance, primaryOwner, accountHolder);
    }

    //Empty constructor
    public StudentChecking() {
    }
}
