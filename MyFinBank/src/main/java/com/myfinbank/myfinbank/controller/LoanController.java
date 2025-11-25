package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.model.LoanApplication;
import com.myfinbank.myfinbank.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public LoanApplication apply(@RequestBody LoanApplication loan) {
        return loanService.apply(loan);
    }

    @PutMapping("/update/{applicationNo}")
    public LoanApplication update(@PathVariable Long applicationNo, @RequestBody LoanApplication updated) {
        return loanService.update(applicationNo, updated);
    }

    @DeleteMapping("/cancel/{applicationNo}")
    public String cancel(@PathVariable Long applicationNo) {
        loanService.cancel(applicationNo);
        return "Loan application canceled";
    }

    @GetMapping("/status/{applicationNo}")
    public LoanApplication status(@PathVariable Long applicationNo) {
        return loanService.status(applicationNo);
    }

    @GetMapping("/pending")
    public List<LoanApplication> pending() {
        return loanService.listpending();
    }

    @PutMapping("/approve/{applicationNo}")
    public LoanApplication approve(@PathVariable Long applicationNo) {
        return loanService.approve(applicationNo);
    }

    @PutMapping("/deny/{applicationNo}")
    public LoanApplication deny(@PathVariable Long applicationNo) {
        return loanService.deny(applicationNo);
    }

    @PutMapping("/abeyance/{applicationNo}")
    public LoanApplication abeyance(@PathVariable Long applicationNo) {
        return loanService.abeyance(applicationNo);
    }
}
