package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banksystem.BankSystem.services.*;

    @RestController
    @RequestMapping("/api")
    public class AccountController {
        @Autowired
        private  accountService accountService;
        @Autowired
        private  customerService customerService;




        @PostMapping("/addAccount/{customerId}")
        public ResponseEntity<Account> createAccountForCustomer(@PathVariable Integer customerId, @RequestBody Account account) {
            Account newAccount = accountService.createAccountForCustomer(account, customerId);
            return ResponseEntity.ok(newAccount);
        }
}
