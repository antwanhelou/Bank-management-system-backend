package com.banksystem.BankSystem.entities.bankaccounts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SavingBankAccount extends BankAccount{

    @Column
    private BigDecimal monthlySaveAmount;

    @Column
    private UUID associateBankAccount;

}
