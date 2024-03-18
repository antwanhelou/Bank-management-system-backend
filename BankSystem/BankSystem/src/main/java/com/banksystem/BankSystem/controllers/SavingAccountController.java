package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.CreateBankAccountRequestDTO;
import com.banksystem.BankSystem.exceptions.BankSystemException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.services.SavingBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/savingAccounts")
public class SavingAccountController {

    @Autowired
    private final SavingBankAccountService savingBankAccountService;

    public SavingAccountController(SavingBankAccountService savingBankAccountService) {
        this.savingBankAccountService = savingBankAccountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestParam final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        return savingBankAccountService.createBankAccount(requestDTO);
    }

    @PostMapping("/withdrawAllSavings/{accountNumber}")
    public ResponseEntity<Map<String, String>> withdrawAllSavings(@PathVariable final String accountNumber) throws BankSystemException {
        return savingBankAccountService.withdrawAllSavings(accountNumber);
    }

}
