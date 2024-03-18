package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.exceptions.*;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.services.JointBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

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

    @DeleteMapping("/suspendAccount/{accountNumber}")
    public ResponseEntity<Map<String, String>> suspendAccount(@PathVariable final String accountNumber) throws BankAccountNotFoundException {
        return jointBankAccountService.suspendAccount(accountNumber);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@RequestBody final DepositOrWithdrawDTO depositDTO) throws BankSystemException {
        return jointBankAccountService.depositMoney(depositDTO);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@RequestBody final DepositOrWithdrawDTO withdrawDTO) throws BankSystemException {
        return jointBankAccountService.withdrawMoney(withdrawDTO);
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BigDecimal> viewBalance(@PathVariable final String accountNumber) throws BankAccountNotFoundException {
        return jointBankAccountService.viewBalance(accountNumber);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@RequestBody final TransferRequestDTO transferRequestDTO) throws BankSystemException {
        return jointBankAccountService.transferMoney(transferRequestDTO);
    }

    @DeleteMapping("/close/{accountNumber}")
    public ResponseEntity<Map<String, String>> closeAccount(@PathVariable final String accountNumber) throws BankAccountNotFoundException, CloseAccountException {
        return jointBankAccountService.closeAccount(accountNumber);
    }
}
