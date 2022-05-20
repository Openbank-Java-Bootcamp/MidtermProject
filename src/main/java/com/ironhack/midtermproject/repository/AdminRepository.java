package com.ironhack.midtermproject.repository;

import com.ironhack.midtermproject.model.users.Admin;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query(value = "SELECT account_id, balance_amount FROM account GROUP BY account_id", nativeQuery = true)
    List<Object[]> findAllBalances();

}
