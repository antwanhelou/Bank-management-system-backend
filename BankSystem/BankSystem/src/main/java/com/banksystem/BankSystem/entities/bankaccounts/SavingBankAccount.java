package com.banksystem.BankSystem.entities.bankaccounts;

import com.banksystem.BankSystem.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SavingBankAccount extends BankAccount{

    @Column
    private BigDecimal monthlySaveAmount;

    @Column
    private String associateBankAccountNumber;

    @Column
    private AccountType associatedType;
}
