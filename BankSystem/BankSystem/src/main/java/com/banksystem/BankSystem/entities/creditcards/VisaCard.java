package com.banksystem.BankSystem.entities.creditcards;

import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import jakarta.persistence.Column;

public class VisaCard extends CreditCard {


    @Column(nullable = false)
    private String expireMonth;

    @Column(nullable = false)
    private String expireYear;

    @Column(nullable = false)
    private String CVV;

}
