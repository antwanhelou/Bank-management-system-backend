package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.CreateBankAccountRequestDTO;
import com.banksystem.BankSystem.DTOs.JointBankAccountDTO;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.services.JointBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/jointAccounts")
public class JointBankAccountController {

    @Autowired
    private final JointBankAccountService jointBankAccountService;

    public JointBankAccountController(JointBankAccountService jointBankAccountService) {
        this.jointBankAccountService = jointBankAccountService;
    }


    @PostMapping("/createAccount")
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestParam final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        return jointBankAccountService.createBankAccount(requestDTO);
    }
}
