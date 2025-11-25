package com.myfinbank.myfinbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_no", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type; // "DEBIT" or "CREDIT"

    private String description;

    @Column(nullable = false)
    private Double balanceAfter;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String remark;
}
