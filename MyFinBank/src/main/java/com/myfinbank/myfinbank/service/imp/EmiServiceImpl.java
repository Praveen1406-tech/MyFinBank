package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Repo.EmiRepository;
import com.myfinbank.myfinbank.model.EMIHistory;
import com.myfinbank.myfinbank.service.EmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmiServiceImpl implements EmiService {

    private final EmiRepository emiRepository;

    @Override
    public double calculateEmi(double principal, double roi, int tenure) {
        double monthlyRate = roi / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure))
                / (Math.pow(1 + monthlyRate, tenure) - 1);
    }

    @Override
    public EMIHistory Savehistory(EMIHistory emiHistory) {
        return emiRepository.save(emiHistory);
    }

    @Override
    public List<EMIHistory> getEMiHistory(Long account_no) {
        return emiRepository.findAll()
                .stream()
                .filter(e -> e.getCustomer().getAccountNo().equals(account_no))
                .toList();
    }

    @Override
    public void deleteHistory(Long id) {
        emiRepository.deleteById(id);
    }
}
