package com.banksystem.BankSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banksystem.BankSystem.services.transactionService;
@RestController
@RequestMapping("/api/transactions")
public class transactionController {

    @Autowired
    private transactionService transactionService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<?> deposit(@PathVariable Integer accountId, @RequestBody double amount) {
        transactionService.addMoney(accountId, amount);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/viewBalance/{accountId}")
    public ResponseEntity<Double> viewBalance(@PathVariable Integer accountId) {
        double balance = transactionService.viewBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<?> withdraw(@PathVariable Integer accountId, @RequestBody double amount) {
        transactionService.withdrawMoney(accountId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam Integer fromAccountId, @RequestParam Integer toAccountId, @RequestParam double amount) {
        transactionService.transferMoney(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok().build();
    }
}
