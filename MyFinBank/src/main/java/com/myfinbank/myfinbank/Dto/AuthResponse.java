package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String role;
    private CustomerDTO customer; // null for admin
}
