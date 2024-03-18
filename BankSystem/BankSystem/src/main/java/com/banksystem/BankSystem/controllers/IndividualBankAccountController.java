package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.exceptions.*;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.services.IndividualBankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

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

    @DeleteMapping("/suspendAccount/{accountNumber}")
    public ResponseEntity<Map<String, String>> suspendAccount(@PathVariable final String accountNumber) throws BankAccountNotFoundException {
        return individualBankAccountService.suspendAccount(accountNumber);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@RequestBody final DepositOrWithdrawDTO depositDTO) throws BankSystemException {
        return individualBankAccountService.depositMoney(depositDTO);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@RequestBody final DepositOrWithdrawDTO withdrawDTO) throws BankSystemException {
        return individualBankAccountService.withdrawMoney(withdrawDTO);
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BigDecimal> viewBalance(@PathVariable final String accountNumber) throws BankAccountNotFoundException {
        return individualBankAccountService.viewBalance(accountNumber);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@RequestBody final TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, InsufficientBalanceException {
        return individualBankAccountService.transferMoney(transferRequestDTO);
    }

    @DeleteMapping("/close/{accountNumber}")
    public ResponseEntity<Map<String, String>> closeAccount(@PathVariable final String accountNumber) throws BankAccountNotFoundException, CloseAccountException {
        return individualBankAccountService.closeAccount(accountNumber);
    }


}
