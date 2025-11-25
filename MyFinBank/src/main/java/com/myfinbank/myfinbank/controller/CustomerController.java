package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.Dto.TransferRequest;
import com.myfinbank.myfinbank.model.Customer;
import com.myfinbank.myfinbank.model.Transaction;
import com.myfinbank.myfinbank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final com.myfinbank.myfinbank.service.TransactionService transactionService;

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        return customerService.register(customer);
    }

    @PutMapping("/update/{accountNo}")
    public Customer update(@PathVariable Long accountNo, @RequestBody Customer customer) {
        return customerService.Update(accountNo, customer);
    }

    @DeleteMapping("/delete/{accountNo}")
    public String delete(@PathVariable Long accountNo) {
        customerService.delete(accountNo);
        return "Customer deleted successfully";
    }

    @GetMapping("/list")
    public List<Customer> listAll() {
        return customerService.listAll();
    }

    @GetMapping("/{accountNo}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long accountNo) {
        return transactionService.getTransactions(accountNo);
    }
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest req) {
        customerService.transfer(req.getFromAcc(), req.getToAcc(), req.getAmount());
        return "Transfer successful";
    }
    @GetMapping("/{accountNo}/mini-statement")
    public List<Transaction> mini(@PathVariable Long accountNo) {
        return transactionService.getMiniStatement(accountNo);
    }




    @GetMapping("/{accountNo}")
    public Customer getById(@PathVariable Long accountNo) {
        return customerService.getById(accountNo);
    }
}
