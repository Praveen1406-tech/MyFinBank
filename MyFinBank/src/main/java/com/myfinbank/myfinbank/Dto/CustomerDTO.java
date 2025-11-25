package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long accountNo;
    private String name;
    private String accountType;
    private Double balance;
    private String email;
}

