package com.myfinbank.myfinbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loanapplication")
public class LoanApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Applicationno;
	
	@ManyToOne
	@JoinColumn(name = "account_no",nullable = false)
	private Customer customer;
	
	@Column(nullable = false)
	private LocalDateTime applicationDateTime =LocalDateTime.now();
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private Integer tenure;
	
	@Column(nullable = false)
	private Double roi;
	
	@Column(nullable = false)
    private String status;


}
