package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banksystem.BankSystem.services.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private final BankAccountService bankAccountService;


    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;

    }


//    @PostMapping("/createBankAccount")
//    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestParam final UUID customerID) {
//        return bankAccountService.createBankAccount(customerID);
//    }

//    @PostMapping("/createBankAccount")
//    public ResponseEntity<BankAccountDTO> createBankAccount()
}
