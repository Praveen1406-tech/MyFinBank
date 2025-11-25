package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Repo.LoanRepository;
import com.myfinbank.myfinbank.model.LoanApplication;
import com.myfinbank.myfinbank.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanApplication apply(LoanApplication loan) {
        loan.setStatus("PENDING");
        return loanRepository.save(loan);
    }

    @Override
    public LoanApplication update(Long applicationNo, LoanApplication updatedLoan) {
        LoanApplication existing = loanRepository.findById(applicationNo)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        existing.setAmount(updatedLoan.getAmount());
        existing.setTenure(updatedLoan.getTenure());
        existing.setRoi(updatedLoan.getRoi());

        return loanRepository.save(existing);
    }

    @Override
    public void cancel(Long application_no) {

        loanRepository.deleteById(application_no);
    }

    @Override
    public LoanApplication status(Long applicationNo) {
        return loanRepository.findById(applicationNo)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public List<LoanApplication> listpending() {
        return loanRepository.findAll()
                .stream()
                .filter(loan -> loan.getStatus().equals("PENDING"))
                .toList();
    }

    @Override
    public LoanApplication approve(Long applicationNo) {
        LoanApplication loan = status(applicationNo);
        loan.setStatus("APPROVED");
        return loanRepository.save(loan);
    }

    @Override
    public LoanApplication deny(Long applicationNo) {
        LoanApplication loan = status(applicationNo);
        loan.setStatus("DENIED");
        return loanRepository.save(loan);
    }

    @Override
    public LoanApplication abeyance(Long applicationNo) {
        LoanApplication loan = status(applicationNo);
        loan.setStatus("ABEYANCE");
        return loanRepository.save(loan);
    }
}