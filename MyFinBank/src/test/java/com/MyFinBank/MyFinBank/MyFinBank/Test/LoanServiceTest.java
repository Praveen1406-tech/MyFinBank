package com.MyFinBank.MyFinBank.MyFinBank.Test;

import com.MyFinBank.MyFinBank.Repo.LoanRepository;
import com.MyFinBank.MyFinBank.model.LoanApplication;
import com.MyFinBank.MyFinBank.service.imp.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    public LoanServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyLoan() {
        LoanApplication loan = new LoanApplication();
        loan.setAmount(200000.0);

        when(loanRepository.save(loan)).thenReturn(loan);

        LoanApplication result = loanService.apply(loan);
        System.out.println("Loan Amount = " + result.getAmount());

        assertEquals(200000.0, result.getAmount());
    }

    @Test
    void testLoanStatus() {
        LoanApplication loan = new LoanApplication();
        loan.setApplicationno(1L);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        LoanApplication result = loanService.status(1L);

        assertNotNull(result);
        assertEquals(1L, result.getApplicationno());
    }
}
