package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class SendOtpRequest {
    private String identifier; // email or admin username
    private String role;       // ADMIN or CUSTOMER
}
