package com.banksystem.BankSystem.entities.creditcards;

import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)

@Table(name="credit_card")
public class VisaCard extends CreditCard {


    @Column(nullable = false)
    private String expireMonth;

    @Column(nullable = false)
    private String expireYear;

    @Column(nullable = false)
    private String CVV;

}
