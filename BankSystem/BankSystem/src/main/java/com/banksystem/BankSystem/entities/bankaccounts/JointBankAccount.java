package com.banksystem.BankSystem.entities.bankaccounts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shared_bank_accounts")
public class JointBankAccount extends BankAccount{

    @Column
    private double maximumDailyWithdrawal;


}
