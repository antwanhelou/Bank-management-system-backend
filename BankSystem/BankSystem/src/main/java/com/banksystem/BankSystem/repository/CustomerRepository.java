package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends BaseUserRepository<Customer> {
}
