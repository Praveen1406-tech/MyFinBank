package com.myfinbank.myfinbank.Repo;

import com.myfinbank.myfinbank.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username); // corrected method name
}
