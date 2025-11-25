package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class CustomerReportDTO {
    private Long totalCustomers;
    private Double totalBalance;
    private Object accountTypeSummary;
}
