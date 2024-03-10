package com.banksystem.BankSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banksystem.BankSystem.services.TransactionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<?> deposit(@PathVariable UUID accountId, @RequestBody double amount) {
        transactionService.addMoney(accountId, amount);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/viewBalance/{accountId}")
    public ResponseEntity<Double> viewBalance(@PathVariable UUID accountId) {
        double balance = transactionService.viewBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<?> withdraw(@PathVariable UUID accountId, @RequestBody double amount) {
        transactionService.withdrawMoney(accountId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam UUID fromAccountId, @RequestParam UUID toAccountId, @RequestParam double amount) {
        transactionService.transferMoney(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok().build();
    }
}
