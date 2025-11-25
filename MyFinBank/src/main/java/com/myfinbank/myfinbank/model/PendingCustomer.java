package com.myfinbank.myfinbank.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_customer")
@Data
public class PendingCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String pan;

    @Column(unique = true)
    private String aadhar;

    @Column(unique = true)
    private String email;

    private String phone;

    private String photoPath;

    private String otp;

    private LocalDateTime otpExpiry;

    private LocalDateTime createdAt = LocalDateTime.now();
}
