package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.model.Customer;

import java.util.List;

public interface CustomerService {

	Customer register(Customer customer);
	Customer Update(Long account_no,Customer customer);
	void delete(Long account_no);
	List<Customer>listAll();
	Customer getByEmail(String email);
	Customer getById(Long account_no);
    void transfer(Long fromAcc, Long toAcc, Double amount);


}
