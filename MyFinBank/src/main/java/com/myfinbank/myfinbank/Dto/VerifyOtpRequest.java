package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String identifier;
    private String otp;
}
