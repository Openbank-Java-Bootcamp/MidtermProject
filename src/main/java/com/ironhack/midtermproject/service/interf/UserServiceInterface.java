package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.users.User;

import java.util.List;

public interface UserServiceInterface {

    User saveUser(User user);
    User getUser(String username);
    List<User> getUsers();

}
