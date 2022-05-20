package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interf.CheckingControllerInterface;
import com.ironhack.midtermproject.service.interf.CheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckingController implements CheckingControllerInterface {

    @Autowired
    private CheckingServiceInterface checkingServiceInterface;


}
