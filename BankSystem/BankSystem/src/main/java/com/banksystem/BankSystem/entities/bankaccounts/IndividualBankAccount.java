package com.banksystem.BankSystem.entities.bankaccounts;


import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "individual_bank_accounts")
public class IndividualBankAccount extends BankAccount{

    @OneToMany(mappedBy = "bankAccount")
    private Set<CreditCard> creditCards;

    @Column
    private Set<UUID> associatedBankAccounts;

}
