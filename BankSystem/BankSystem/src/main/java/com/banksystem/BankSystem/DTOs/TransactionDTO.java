package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.TransactionType;

import java.time.Instant;
import java.util.UUID;

public class TransactionDTO {

    private UUID id;

    private Instant transactionDate;

    private double amount;

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
