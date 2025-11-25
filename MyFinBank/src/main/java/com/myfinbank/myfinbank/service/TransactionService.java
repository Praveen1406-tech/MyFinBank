package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions(Long accountNo);
    List<Transaction> getMiniStatement(Long accountNo);


}
