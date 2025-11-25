package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String email;
    private String newPassword;
    private String otp;
}
