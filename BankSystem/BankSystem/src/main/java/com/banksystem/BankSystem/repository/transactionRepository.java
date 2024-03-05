package com.banksystem.BankSystem.repository;


import com.banksystem.BankSystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface transactionRepository extends JpaRepository<Transaction, Integer> {
}
