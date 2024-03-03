package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface accountRepository extends JpaRepository<Account, Integer> {
}
