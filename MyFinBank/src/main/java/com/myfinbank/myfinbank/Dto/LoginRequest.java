package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // for admin (or email for customer)
    private String password;
}
