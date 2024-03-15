package com.banksystem.BankSystem.entities.bankaccounts;


import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "individual_bank_accounts")
public class IndividualBankAccount extends BankAccount{

    @OneToMany(mappedBy = "bankAccount")
    private Set<CreditCard> creditCards;

}
