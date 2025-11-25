package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c.accountType, COUNT(c) FROM Customer c GROUP BY c.accountType")
    List<Object[]> getCustomerCountByType();

    @Query("SELECT COALESCE(SUM(c.balance), 0) FROM Customer c")
    Double getTotalBalance();

}
