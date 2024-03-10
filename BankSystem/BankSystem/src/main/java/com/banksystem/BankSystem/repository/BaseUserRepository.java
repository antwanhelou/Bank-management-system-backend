package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseUserRepository<T> extends JpaRepository<T, UUID> {
}
