package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop10ByCustomerAccountNoOrderByCreatedAtDesc(Long accountNo);

    List<Transaction> findByCustomerAccountNoOrderByCreatedAtDesc(Long accountNo);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.customer.accountNo = :accountNo AND t.type = 'DEBIT' " +
            "AND t.createdAt BETWEEN :start AND :end")
    Double getTotalDebitsForDay(
            @Param("accountNo") Long accountNo,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
