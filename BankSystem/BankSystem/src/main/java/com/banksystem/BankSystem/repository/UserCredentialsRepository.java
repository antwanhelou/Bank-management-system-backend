package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.users.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {

    @Query("SELECT uc FROM UserCredentials uc " +
            "WHERE uc.userName = :userName " +
            "AND uc.password = :password")
    Optional<UserCredentials> findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Query("SELECT uc FROM UserCredentials uc " +
            "WHERE uc.userName = :userName ")
    Optional<UserCredentials> findByUserName(@Param("userName") String userName);

    @Query("SELECT uc FROM UserCredentials uc " +
            "WHERE uc.userName = :userName " +
            "AND uc.email = :email"
    )
    Optional<UserCredentials> findByUserNameAndEmail(@Param("userName") final String userName, @Param("email") final String email);
}
