package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Repo.TransactionRepository;
import com.myfinbank.myfinbank.model.Transaction;
import com.myfinbank.myfinbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions(Long accountNo) {
        return transactionRepository.findByCustomerAccountNoOrderByCreatedAtDesc(accountNo);
    }
    @Override
    public List<Transaction> getMiniStatement(Long accountNo) {
        return transactionRepository.findTop10ByCustomerAccountNoOrderByCreatedAtDesc(accountNo);
    }

}
