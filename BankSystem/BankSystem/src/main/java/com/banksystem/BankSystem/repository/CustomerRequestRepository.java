package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, UUID> {
}
