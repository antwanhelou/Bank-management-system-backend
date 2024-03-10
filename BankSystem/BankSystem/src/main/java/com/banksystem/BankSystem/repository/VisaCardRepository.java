package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.creditcards.VisaCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VisaCardRepository extends CreditCardRepository<VisaCard> {
}
