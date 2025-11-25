package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.Dto.CustomerReportDTO;
import com.myfinbank.myfinbank.Dto.LoanStatusSummaryDTO;
import com.myfinbank.myfinbank.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/loans")
    public List<LoanStatusSummaryDTO> loanReport() {
        return reportService.getLoanReport();
    }

    @GetMapping("/customers")
    public CustomerReportDTO customerReport() {
        return reportService.getCustomerReport();
    }
}
