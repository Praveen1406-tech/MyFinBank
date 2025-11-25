package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final JavaMailSender mailSender;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();

    @Override
    public void sendOtp(String identifier, String role) {

        String otp = String.format("%06d", new Random().nextInt(999999));

        otpStore.put(identifier, otp);
        otpExpiry.put(identifier, System.currentTimeMillis() + 2 * 60 * 1000); // 2 mins

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(identifier);
        message.setSubject("Your MyFinBank OTP");
        message.setText("Your OTP is: " + otp + "\nValid for 2 minutes.");

        mailSender.send(message);

        System.out.println("OTP Sent to " + identifier + " = " + otp);
    }

    @Override
    public boolean verifyOtp(String identifier, String otp) {

        if (!otpStore.containsKey(identifier)) return false;

        if (System.currentTimeMillis() > otpExpiry.get(identifier)) {
            otpStore.remove(identifier);
            otpExpiry.remove(identifier);
            return false; // expired
        }

        boolean isValid = otp.equals(otpStore.get(identifier));

        if (isValid) {
            otpStore.remove(identifier);
            otpExpiry.remove(identifier);
        }

        return isValid;
    }
}
