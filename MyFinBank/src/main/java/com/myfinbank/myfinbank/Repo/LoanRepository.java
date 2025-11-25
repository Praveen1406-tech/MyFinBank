package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LoanRepository extends JpaRepository<LoanApplication, Long>{

    @Query("SELECT l.status, COUNT(l), COALESCE(SUM(l.amount),0) FROM LoanApplication l GROUP BY l.status")
    List<Object[]> getLoanSummary();

}
