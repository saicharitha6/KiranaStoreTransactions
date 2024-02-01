package com.example.kirana_store.controller;

import java.util.List;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kirana_store.model.KiranaStore;
import com.example.kirana_store.service.KiranaService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private KiranaService transactionService;

    @PostMapping
    public ResponseEntity<KiranaStore> recordTransaction(@RequestBody KiranaStore transaction) {
        KiranaStore savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/daily-report/{date}")
    public ResponseEntity<List<KiranaStore>> getDailyTransactionsReport(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<KiranaStore> dailyReport = transactionService.getDailyTransactionsReport(date.atStartOfDay());
        return ResponseEntity.ok(dailyReport);
    }
}
