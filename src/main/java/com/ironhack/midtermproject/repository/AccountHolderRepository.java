package com.ironhack.midtermproject.repository;

import com.ironhack.midtermproject.model.users.AccountHolder;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {

    @Query(value = "SELECT id FROM user WHERE username = :username", nativeQuery = true)
    Integer findIdByUsername(String username);

    @Query(value = "SELECT accounts_id FROM account_and_account_holder WHERE account_holder_id = :accountHolderId", nativeQuery = true)
    List<Integer> findAccountIdByAccountHolderId(Integer accountHolderId);

    @Query(value = "SELECT  account_id, balance_amount FROM account WHERE account_id IN :accountId ORDER BY account_id", nativeQuery = true)
    List<Object[]> findBalancesByAccountId(List<Integer> accountId);

    @Query(value = "SELECT accounts_id FROM account_and_account_holder WHERE account_id = :id", nativeQuery = true)
    Integer findAccountsInJoinedTableByAccountId(Integer id);
}
