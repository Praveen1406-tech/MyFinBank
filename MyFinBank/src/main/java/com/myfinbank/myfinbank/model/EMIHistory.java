package com.myfinbank.myfinbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="emihistory")
public class EMIHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "account_no",nullable = false)
	private Customer customer;
	
	@Column(nullable = false)
	private Integer tenure;
	
	@Column(nullable = false)
	private Double roi;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
    private Double calculatedEmi;

    @Column(nullable = false)
    private LocalDateTime calculatedOn = LocalDateTime.now();
	

}
