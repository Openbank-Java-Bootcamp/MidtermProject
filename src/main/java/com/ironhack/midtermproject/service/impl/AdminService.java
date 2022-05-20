package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.repository.AdminRepository;
import com.ironhack.midtermproject.service.interf.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    private AdminRepository adminRepository;

    public List<Object[]> findAllBalances(){
        return adminRepository.findAllBalances();
    }





}
