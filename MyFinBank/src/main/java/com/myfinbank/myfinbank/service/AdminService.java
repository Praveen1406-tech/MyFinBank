package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.model.Admin;

public interface AdminService {
	Admin login(String username,String password);

    Admin getByUsername(String identifier);
}
