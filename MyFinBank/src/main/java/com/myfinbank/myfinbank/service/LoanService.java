package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.model.LoanApplication;

import java.util.List;

public interface LoanService {
	
	LoanApplication apply(LoanApplication loan);
	LoanApplication update(Long account_no,LoanApplication updateLoan);
	void cancel(Long applicationno);
	LoanApplication status(Long applicationno);
	List<LoanApplication> listpending();
	LoanApplication approve(Long applicationno);
	LoanApplication deny(Long applicationno);
	LoanApplication abeyance(Long applicationno);

}
