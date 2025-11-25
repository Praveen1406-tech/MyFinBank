package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class LoanStatusSummaryDTO {
    private String status;
    private Long count;
    private Double totalAmount;
}
