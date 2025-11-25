package com.myfinbank.myfinbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "approval_requests")
public class ApprovalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // LOAN or TRANSFER
    private Long referenceId; 
    private String status; // PENDING, APPROVED, REJECTED
    private String remarks;

    private LocalDateTime createdAt;
}
