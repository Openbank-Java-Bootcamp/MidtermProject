package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.RoleControllerInterface;
import com.ironhack.midtermproject.model.DTO.RoleToUserDTO;
import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.service.impl.RoleService;
import com.ironhack.midtermproject.service.impl.UserService;
import com.ironhack.midtermproject.service.interf.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController implements RoleControllerInterface {

    @Autowired
    private RoleServiceInterface roleServiceInterface;



}
