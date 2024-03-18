package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository<T extends BankAccount> extends JpaRepository<T, UUID> {

    public Optional<T> findByAccountNumber(String accountNumber);

    @Query(value = "SELECT a FROM (" +
            "SELECT i AS a FROM IndividualBankAccount i WHERE i.accountNumber = :accountNumber " +
            "UNION ALL " +
            "SELECT j AS a FROM JointBankAccount j WHERE j.accountNumber = :accountNumber " +
            "UNION ALL " +
            "SELECT k AS a FROM SavingBankAccount k WHERE k.accountNumber = :accountNumber) a")
    public Optional<BankAccount> findByAccountNumberAllTables(String accountNumber);
}
