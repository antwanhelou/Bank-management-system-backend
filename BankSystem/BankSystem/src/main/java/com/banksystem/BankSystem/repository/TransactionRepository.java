package com.banksystem.BankSystem.repository;


import com.banksystem.BankSystem.entities.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
