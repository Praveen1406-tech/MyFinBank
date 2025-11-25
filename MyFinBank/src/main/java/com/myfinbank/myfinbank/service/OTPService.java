package com.myfinbank.myfinbank.service;

public interface OTPService {
    void sendOtp(String identifier, String role);
    boolean verifyOtp(String identifier, String otp);
}
