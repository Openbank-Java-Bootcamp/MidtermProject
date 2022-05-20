package com.ironhack.midtermproject.repository;

import com.ironhack.midtermproject.model.users.ThirdPartyUser;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyUserRepository extends JpaRepository<ThirdPartyUser, Integer> {

    ThirdPartyUser findByHashKey(String hashKey);
}
