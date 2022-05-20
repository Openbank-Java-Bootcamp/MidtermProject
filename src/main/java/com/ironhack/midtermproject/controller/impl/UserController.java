package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.UserControllerInterface;
import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.RoleToUserDTO;
import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.users.User;
import com.ironhack.midtermproject.service.impl.UserService;
import com.ironhack.midtermproject.service.interf.AdminServiceInterface;
import com.ironhack.midtermproject.service.interf.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController implements UserControllerInterface {

    @Autowired
    private UserServiceInterface userServiceInterface;










}
