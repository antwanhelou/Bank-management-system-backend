package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.InsufficientFundsException;
import com.banksystem.BankSystem.services.BankAccountService;
import com.banksystem.BankSystem.services.IndividualBankAccountService;
import com.banksystem.BankSystem.services.JointBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {



    @Autowired
    private BankAccountService bankAccountService;

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
@GetMapping("/viewBalance/{accountId}")
public ResponseEntity<BigDecimal> viewBalance(@PathVariable UUID accountId) {
    BigDecimal balance = bankAccountService.viewBalance(accountId);
    return ResponseEntity.ok(balance);
}

    @PostMapping("/deposit")
    public ResponseEntity<DepositDTO> deposit(@RequestBody DepositDTO depositDTO) throws Throwable {
        bankAccountService.depositMoney(depositDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<DepositDTO> withdraw(@RequestBody DepositDTO withdrawDTO) throws BankAccountNotFoundException {
        try {
            bankAccountService.withdrawMoney(withdrawDTO);
            return  new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {
            bankAccountService.transferMoney(transferRequestDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BankAccountNotFoundException | InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the transfer");
        }
    }

}
