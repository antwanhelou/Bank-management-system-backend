package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.loans.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanDTO {

    private BigDecimal amount;
    private String accountNumber;
    private boolean isPaid;
    private BigDecimal interestRate;
    private LocalDate dueDate;


    public void set(Loan loan){
        this.amount = loan.getAmount();
        this.accountNumber = loan.getBankAccount().getAccountNumber();
        this.isPaid = loan.isPaid();
        this.interestRate = loan.getInterestRate();
        this.dueDate = loan.getDueDate();
    }

}