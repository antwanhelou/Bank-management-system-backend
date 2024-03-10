package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import com.banksystem.BankSystem.repository.TransactionRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void addMoney(UUID accountId, double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        recordTransaction(bankAccount, amount, TransactionType.DEPOSIT);
    }
    @Transactional
    public double viewBalance(UUID accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        bankAccount.getBalance();


        return bankAccount.getBalance();
    }

    @Transactional
    public void withdrawMoney(UUID accountId, double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        if (bankAccount.getBalance() - amount < -1000) {
            throw new RuntimeException("Insufficient funds. Account cannot go below -1000.");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
        recordTransaction(bankAccount, -amount, TransactionType.WITHDRAWAL);
    }

    @Transactional
    public void transferMoney(UUID fromAccountId, UUID toAccountId, double amount) {
        withdrawMoney(fromAccountId, amount);
        addMoney(toAccountId, amount);
        // Note: Transaction recording for transfer is handled in withdrawMoney and addMoney methods.
    }

    private void recordTransaction(BankAccount bankAccount, double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(Instant.now());
        transaction.setTransactionType(type);
        transactionRepository.save(transaction);
    }
}
