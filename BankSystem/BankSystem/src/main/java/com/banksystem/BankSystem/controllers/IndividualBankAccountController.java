package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.CreateBankAccountRequestDTO;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.services.IndividualBankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/individualAccounts")
public class IndividualBankAccountController {

    private final IndividualBankAccountService individualBankAccountService;

    public IndividualBankAccountController(IndividualBankAccountService individualBankAccountService) {
        this.individualBankAccountService = individualBankAccountService;
    }


    @PostMapping("/createAccount")
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestParam final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {

        return individualBankAccountService.createBankAccount(requestDTO);
    }

    @DeleteMapping("/suspendAccount/{accountID}")
    public ResponseEntity<Map<String, String>> suspendAccount(@PathVariable UUID accountID) throws BankAccountNotFoundException {
        return individualBankAccountService.suspendAccount(accountID);
    }

}
