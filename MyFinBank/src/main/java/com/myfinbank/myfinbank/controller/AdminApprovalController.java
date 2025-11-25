package com.myfinbank.myfinbank.controller;

import com.myfinbank.myfinbank.Repo.ApprovalRequestRepository;
import com.myfinbank.myfinbank.model.ApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/approvals")
@RequiredArgsConstructor
public class AdminApprovalController {

    private final ApprovalRequestRepository repo;

    @GetMapping("/pending")
    public List<ApprovalRequest> listPending() {
        return repo.findByStatus("PENDING");
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        ApprovalRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        req.setStatus("APPROVED");
        repo.save(req);

        return "Approved";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id, @RequestParam String remarks) {
        ApprovalRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        req.setStatus("REJECTED");
        req.setRemarks(remarks);
        repo.save(req);

        return "Rejected";
    }
}
