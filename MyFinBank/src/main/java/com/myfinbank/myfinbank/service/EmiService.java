package com.myfinbank.myfinbank.service;

import com.myfinbank.myfinbank.model.EMIHistory;

import java.util.List;

public interface EmiService {

	double calculateEmi(double principal,double roi,int tenure);
	EMIHistory Savehistory(EMIHistory emiHistory);
	//List<EMIHistory> getEmiHistory(Long account_no);

    List<EMIHistory> getEMiHistory(Long account_no);

    void deleteHistory(Long id);
}
