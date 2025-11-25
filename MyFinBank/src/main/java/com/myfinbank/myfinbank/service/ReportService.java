package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.Dto.CustomerReportDTO;
import com.myfinbank.myfinbank.Dto.LoanStatusSummaryDTO;

import java.util.List;

public interface ReportService {
    List<LoanStatusSummaryDTO> getLoanReport();
    CustomerReportDTO getCustomerReport();
}
