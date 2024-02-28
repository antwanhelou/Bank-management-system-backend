package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  customerRepository extends JpaRepository<Customer, Integer> {
}
