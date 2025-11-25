package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.model.EMIHistory;
import com.myfinbank.myfinbank.service.EmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emi")
@RequiredArgsConstructor
public class EmiController {

    private final EmiService emiService;

    @GetMapping("/calculate")
    public double calculate(
            @RequestParam double amount,
            @RequestParam double roi,
            @RequestParam int tenure) {

        return emiService.calculateEmi(amount, roi, tenure);
    }

    @PostMapping("/save")
    public EMIHistory saveHistory(@RequestBody EMIHistory emiHistory) {
        return emiService.Savehistory(emiHistory);
    }

    @GetMapping("/history/{accountNo}")
    public List<EMIHistory> getHistory(@PathVariable Long accountNo) {
        return emiService.getEMiHistory(accountNo);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHistory(@PathVariable Long id) {
        emiService.deleteHistory(id);
        return "EMI history deleted";
    }
}
