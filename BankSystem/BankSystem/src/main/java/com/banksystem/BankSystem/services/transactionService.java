package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.Account;
import com.banksystem.BankSystem.entities.Transaction;
import com.banksystem.BankSystem.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.banksystem.BankSystem.repository.accountRepository;

import com.banksystem.BankSystem.repository.transactionRepository;
import java.time.LocalDateTime;

@Service
public class transactionService {

    @Autowired
    private accountRepository accountRepository;

    @Autowired
    private transactionRepository transactionRepository;

    @Transactional
    public void addMoney(Integer accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        recordTransaction(account, amount, TransactionType.DEPOSIT);
    }
    @Transactional
    public double viewBalance(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        account.getBalance();


        return account.getBalance();
    }

    @Transactional
    public void withdrawMoney(Integer accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for this id :: " + accountId));
        if (account.getBalance() - amount < -1000) {
            throw new RuntimeException("Insufficient funds. Account cannot go below -1000.");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        recordTransaction(account, -amount, TransactionType.WITHDRAWAL);
    }

    @Transactional
    public void transferMoney(Integer fromAccountId, Integer toAccountId, double amount) {
        withdrawMoney(fromAccountId, amount);
        addMoney(toAccountId, amount);
        // Note: Transaction recording for transfer is handled in withdrawMoney and addMoney methods.
    }

    private void recordTransaction(Account account, double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(type);
        transactionRepository.save(transaction);
    }
}
