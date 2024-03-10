package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banksystem.BankSystem.services.*;

import java.util.UUID;

@RestController
    @RequestMapping("/api")
    public class AccountController {
        @Autowired
        private BankAccountService bankAccountService;
        @Autowired
        private CustomerService customerService;




        @PostMapping("/addAccount/{customerId}")
        public ResponseEntity<BankAccount> createAccountForCustomer(@PathVariable UUID customerId, @RequestBody BankAccount bankAccount) {
            BankAccount newBankAccount = bankAccountService.createAccountForCustomer(bankAccount, customerId);
            return ResponseEntity.ok(newBankAccount);
        }
}
