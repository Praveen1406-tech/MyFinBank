package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long fromAcc;
    private Long toAcc;
    private Double amount;
    private String remark;
}
