package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccountDTO {

    private UUID id;

    private String accountNumber;

    private AccountStatus status;

    private AccountStatus accountStatus;

    private double balance;

    private double minimumBalance;

    private String bankCode;

    private String branchCode;

    private List<CreditCardDTO> creditCardDTOs;

    private List<TransactionDTO> transactionsDTOs;


    public void set(BankAccount bankAccount){
        this.id = bankAccount.getId();
        this.accountNumber = bankAccount.getAccountNumber();
        this.status = bankAccount.getAccountStatus();
        this.balance = bankAccount.getBalance();
        this.minimumBalance = bankAccount.getMinimumBalance();
        this.bankCode = bankAccount.getBankCode();
        this.branchCode = bankAccount.getBranchCode();
        this.creditCardDTOs = new ArrayList<>();
        for(CreditCard creditCard: bankAccount.getCreditCards()){
            CreditCardDTO creditCardDTO = new CreditCardDTO();
            creditCardDTO.set(creditCard);
            this.creditCardDTOs.add(creditCardDTO);
        }
        this.transactionsDTOs = new ArrayList<>();
        for(Transaction transaction: bankAccount.getTransactions()){
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.set(transaction);
            this.transactionsDTOs.add(transactionDTO);
        }

    }
}
