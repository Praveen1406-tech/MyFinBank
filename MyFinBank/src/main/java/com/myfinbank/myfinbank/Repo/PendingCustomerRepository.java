package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.PendingCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingCustomerRepository extends JpaRepository<PendingCustomer, Long> {
    Optional<PendingCustomer> findByEmail(String email);
}

