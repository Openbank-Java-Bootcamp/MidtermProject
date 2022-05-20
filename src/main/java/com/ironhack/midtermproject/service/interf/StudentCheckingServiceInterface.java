package com.ironhack.midtermproject.service.interf;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.StudentChecking;

public interface StudentCheckingServiceInterface {

    StudentChecking saveStudentChecking(CheckingDTO checkingDTO);
}
