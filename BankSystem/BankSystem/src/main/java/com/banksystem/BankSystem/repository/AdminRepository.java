package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends BaseUserRepository<Admin> {
}
