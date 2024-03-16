package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.creditcards.CreditCard;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardDTO {

    private UUID id;

    private String cardNumber;

    private BigDecimal creditLimit;


    public void set(CreditCard creditCard){
        id = creditCard.getId();
        cardNumber = creditCard.getCardNumber();
        creditLimit = creditCard.getCreditLimit();

    }
}
