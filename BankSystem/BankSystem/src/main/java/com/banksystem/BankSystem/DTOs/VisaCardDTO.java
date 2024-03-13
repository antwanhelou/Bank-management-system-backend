package com.banksystem.BankSystem.DTOs;


import com.banksystem.BankSystem.entities.creditcards.VisaCard;

public class VisaCardDTO extends CreditCardDTO{

    private String expirationMonth;

    private String expirationYear;

    private String CVV;


    public void set(VisaCard visaCard){
        super.set(visaCard);
        this.expirationMonth = visaCard.getExpirationMonth();
        this.expirationYear = visaCard.getExpirationYear();
        this.CVV = visaCard.getCVV();
    }
}
