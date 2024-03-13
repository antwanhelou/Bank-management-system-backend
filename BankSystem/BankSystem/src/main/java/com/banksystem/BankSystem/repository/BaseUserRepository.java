package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.BaseUser;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BaseUserRepository<T extends BaseUser> extends JpaRepository<T, UUID> {


    @Query()
    Optional<T> findUserByUserCredentialsID(UUID userCredentialsID);

}
