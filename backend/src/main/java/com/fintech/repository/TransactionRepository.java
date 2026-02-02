package com.fintech.repository;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
    List<Transaction> findByAccountAndTransactionDateBetween(
        Account account, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
}
