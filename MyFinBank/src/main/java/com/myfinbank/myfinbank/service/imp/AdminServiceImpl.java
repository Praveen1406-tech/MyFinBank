package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Repo.AdminRepository;
import com.myfinbank.myfinbank.model.Admin;
import com.myfinbank.myfinbank.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin login(String username, String password) {

        Admin admin = (Admin)adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return admin;
    }

    @Override
    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }
}
