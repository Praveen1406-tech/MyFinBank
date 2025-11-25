package com.myfinbank.myfinbank.Dto;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String pan;
    private String aadhar;
    private String email;
    private String phone;
}

