package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.model.users.User;
import com.ironhack.midtermproject.repository.RoleRepository;
import com.ironhack.midtermproject.repository.UserRepository;
import com.ironhack.midtermproject.service.interf.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepository;



}
