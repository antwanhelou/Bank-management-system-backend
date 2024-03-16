package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionDTO {

    private UUID id;

    private Instant transactionDate;

    private BigDecimal amount;

    private TransactionType transactionType;

    private String description;

    private Currency currency;

    private UUID customerID;

    public void set(Transaction transaction){
        this.id = transaction.getId();
        this.transactionDate = transaction.getTransactionDate();
        this.amount = transaction.getAmount();
        this.transactionType = transaction.getTransactionType();
        this.description = transaction.getDescription();
        this.currency = transaction.getCurrency();
        this.customerID = transaction.getCustomerID();
    }

}
