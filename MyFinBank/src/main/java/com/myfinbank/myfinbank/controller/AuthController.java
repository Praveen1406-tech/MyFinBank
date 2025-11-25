package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.Dto.*;
import com.myfinbank.myfinbank.Repo.CustomerRepository;
import com.myfinbank.myfinbank.Repo.PendingCustomerRepository;
import com.myfinbank.myfinbank.model.Admin;
import com.myfinbank.myfinbank.model.Customer;
import com.myfinbank.myfinbank.model.PendingCustomer;
import com.myfinbank.myfinbank.service.AdminService;
import com.myfinbank.myfinbank.service.CustomerService;
import com.myfinbank.myfinbank.service.EmailService;
import com.myfinbank.myfinbank.service.OTPService;
import com.myfinbank.myfinbank.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;

    private final PendingCustomerRepository pendingCustomerRepository;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    // -------------------- ADMIN LOGIN ----------------------
    @PostMapping("/admin/login")
    public AuthResponse adminLogin(@RequestBody LoginRequest request) {

        Admin admin = adminService.login(request.getUsername(), request.getPassword());

        String accessToken = jwtUtil.generateAccessToken(admin.getUsername(), "ADMIN");
        String refreshToken = jwtUtil.generateRefreshToken(admin.getUsername(), "ADMIN");

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setRole("ADMIN");

        return response;
    }

    // -------------------- CUSTOMER LOGIN ----------------------
    @PostMapping("/customer/login")
    public String customerLogin(@RequestBody LoginRequest request) {

        Customer customer = customerService.getByEmail(request.getUsername());

        if (customer.getPassword() == null) {
            throw new RuntimeException("Please set your password first.");
        }

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Step 1 → Password correct → Send OTP
        otpService.sendOtp(customer.getEmail(), "CUSTOMER");

        return "Password verified. OTP sent to " + customer.getEmail();
    }



    // -------------------- REFRESH TOKEN ----------------------
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam String refreshToken) {

        if (!jwtUtil.isTokenValid(refreshToken))
            throw new RuntimeException("Invalid refresh token");

        String username = jwtUtil.extractUsername(refreshToken);
        String role = jwtUtil.extractRole(refreshToken);

        String newAccess = jwtUtil.generateAccessToken(username, role);
        String newRefresh = jwtUtil.generateRefreshToken(username, role);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(newAccess);
        response.setRefreshToken(newRefresh);
        response.setRole(role);

        return response;
    }

    @PostMapping(
            value = "/customer/register",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    public String registerCustomer(
            @RequestPart("data") String jsonString,
            @RequestPart("photo") MultipartFile photo
    ) throws Exception {
        System.out.println(">>> MULTIPART RECEIVED");
        System.out.println("DATA = " + jsonString);
        System.out.println("PHOTO = " + photo.getOriginalFilename());

        ObjectMapper mapper = new ObjectMapper();
        CustomerRegistrationRequest request =
                mapper.readValue(jsonString, CustomerRegistrationRequest.class);

        // 1. Upload directory
        Files.createDirectories(Paths.get("uploads"));

        // 2. Save file
        String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        Path filePath = Paths.get("uploads", fileName);
        Files.write(filePath, photo.getBytes());

        // 3. Save pending customer
        PendingCustomer pc = new PendingCustomer();
        pc.setFirstName(request.getFirstName());
        pc.setLastName(request.getLastName());
        pc.setPan(request.getPan());
        pc.setAadhar(request.getAadhar());
        pc.setPhone(request.getPhone());
        pc.setEmail(request.getEmail());
        pc.setPhotoPath(filePath.toString());

        String otp = String.format("%06d", new Random().nextInt(999999));
        pc.setOtp(otp);
        pc.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        pendingCustomerRepository.save(pc);

        emailService.sendOtp(pc.getEmail(), otp);

        return "OTP sent to " + pc.getEmail();
    }



    // -------------------- VERIFY REGISTRATION ----------------------
    @PostMapping("/customer/verify-registration")
    public String verifyRegistration(@RequestParam String email, @RequestParam String otp) {

        PendingCustomer pc = pendingCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!pc.getOtp().equals(otp))
            throw new RuntimeException("Invalid OTP");

        if (pc.getOtpExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        // ---- Create Bank Account ----
        Customer customer = new Customer();
        customer.setName(pc.getFirstName() + " " + pc.getLastName());
        customer.setEmail(pc.getEmail());
        customer.setAccountType("SAVINGS");
        customer.setBalance(0.0);

        // DO NOT SET ANY DEFAULT PASSWORD
        customer.setPassword(null);

        Customer saved = customerRepository.save(customer);

        // delete pending customer
        pendingCustomerRepository.delete(pc);

        return "Customer registered successfully. Account No: " + saved.getAccountNo();
    }

    @PostMapping("/customer/set-password")
    public String setPassword(@RequestBody SetPasswordRequest req) {

        if (!otpService.verifyOtp(req.getEmail(), req.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        Customer customer = customerService.getByEmail(req.getEmail());
        customer.setPassword(passwordEncoder.encode(req.getNewPassword()));

        customerService.register(customer);

        return "Password set successfully. You can now log in.";
    }




    // -------------------- SEND OTP ----------------------
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody SendOtpRequest request) {

        otpService.sendOtp(request.getIdentifier(), request.getRole());
        return "OTP sent successfully";
    }

    // -------------------- VERIFY OTP LOGIN ----------------------
    @PostMapping("/verify-otp")
    public AuthResponse verifyOtp(@RequestBody VerifyOtpRequest request) {

        if (!otpService.verifyOtp(request.getIdentifier(), request.getOtp()))
            throw new RuntimeException("Invalid OTP");

        Admin admin = null;
        Customer customer = null;

        try {
            admin = adminService.getByUsername(request.getIdentifier());
        } catch (Exception ignored) {}

        AuthResponse response = new AuthResponse();

        if (admin != null) {

            String access = jwtUtil.generateAccessToken(admin.getUsername(), "ADMIN");
            String refresh = jwtUtil.generateRefreshToken(admin.getUsername(), "ADMIN");

            response.setAccessToken(access);
            response.setRefreshToken(refresh);
            response.setRole("ADMIN");
            return response;
        }

        customer = customerService.getByEmail(request.getIdentifier());

        String access = jwtUtil.generateAccessToken(customer.getEmail(), "CUSTOMER");
        String refresh = jwtUtil.generateRefreshToken(customer.getEmail(), "CUSTOMER");

        CustomerDTO dto = new CustomerDTO();
        dto.setAccountNo(customer.getAccountNo());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setAccountType(customer.getAccountType());
        dto.setBalance(customer.getBalance());

        response.setAccessToken(access);
        response.setRefreshToken(refresh);
        response.setRole("CUSTOMER");
        response.setCustomer(dto);

        return response;
    }
}
