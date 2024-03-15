package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository<T extends BankAccount> extends JpaRepository<T, UUID> {

    public Optional<BankAccount> findByBankAccountNumber(String accountNumber);
}
