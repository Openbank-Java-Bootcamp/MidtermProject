package com.ironhack.midtermproject.model.users;

import com.ironhack.midtermproject.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "user")
public class Admin extends User{

    public Admin(String name, String password, String username) {
        super(name, password, username);
    }

    public Admin() {
    }
}
