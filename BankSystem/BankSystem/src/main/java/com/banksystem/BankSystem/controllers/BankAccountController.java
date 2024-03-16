package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.IndividualBankAccountDTO;
import com.banksystem.BankSystem.DTOs.JointBankAccountDTO;
import com.banksystem.BankSystem.services.IndividualBankAccountService;
import com.banksystem.BankSystem.services.JointBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {




//    public BankAccountController(BankAccountService bankAccountService) {
//        this.bankAccountService = bankAccountService;
//
//    }

//    @PostMapping("/loans/provide")
//    public ResponseEntity<?> provideLoan(@PathVariable UUID accountId,
//                                         @RequestBody LoanDTO loanDTO) {
//        Loan loan = bankAccountService.provideLoanToCustomer(accountId, loanDTO.getAmount(), loanDTO.getInterestRate(), loanDTO.getDueDate());
//        return ResponseEntity.ok(loan); // Convert to DTO as needed
//    }
//
//    @PostMapping("/loans/repay")
//    public ResponseEntity<?> repayLoan(
//                                       @RequestBody RepaymentDTO repaymentDTO) {
//        BankAccount bankAccount = bankAccountService.makeLoanRepayment(repaymentDTO.getAccountId(), repaymentDTO.getLoanId(), repaymentDTO.getRepaymentAmount());
//        return ResponseEntity.ok(bankAccount); // Convert to DTO as needed
//    }
}
