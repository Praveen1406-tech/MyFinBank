package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.model.Admin;
import com.myfinbank.myfinbank.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestParam String username, @RequestParam String password) {
        return adminService.login(username, password);
    }
}
