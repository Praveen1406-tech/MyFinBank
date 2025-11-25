package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Dto.CustomerReportDTO;
import com.myfinbank.myfinbank.Dto.LoanStatusSummaryDTO;
import com.myfinbank.myfinbank.Repo.CustomerRepository;
import com.myfinbank.myfinbank.Repo.LoanRepository;
import com.myfinbank.myfinbank.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<LoanStatusSummaryDTO> getLoanReport() {

        List<Object[]> data = loanRepository.getLoanSummary();

        return data.stream().map(row -> {
            LoanStatusSummaryDTO dto = new LoanStatusSummaryDTO();
            dto.setStatus((String) row[0]);
            dto.setCount((Long) row[1]);
            dto.setTotalAmount((Double) row[2]);
            return dto;
        }).toList();
    }

    @Override
    public CustomerReportDTO getCustomerReport() {

        CustomerReportDTO dto = new CustomerReportDTO();

        dto.setTotalCustomers(customerRepository.count());
        dto.setTotalBalance(customerRepository.getTotalBalance());
        dto.setAccountTypeSummary(customerRepository.getCustomerCountByType());

        return dto;
    }
}
