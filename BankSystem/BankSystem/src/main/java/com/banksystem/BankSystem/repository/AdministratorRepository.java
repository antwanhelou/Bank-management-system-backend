package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.Administrator;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdministratorRepository extends BaseUserRepository<Administrator> {


    @Query("SELECT a FROM Administrator a JOIN a.tasks t " +
            "WHERE a.isAuthorized = true " +
            "GROUP BY a " +
            "HAVING COUNT(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 END) = 0 " +
            "ORDER BY COUNT(t) " +
            "LIMIT 1")
    public Optional<Administrator> findMostAvailableAdministrator();
}
