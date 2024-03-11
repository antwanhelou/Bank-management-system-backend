package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository<T extends CreditCard> extends JpaRepository<T, UUID> {


//    @Query("SELECT card FROM ")
//    Optional<T> findCardByNumberAndExpirationDate(String cardNumber, String expirationMonth, String expirationYear);
}
