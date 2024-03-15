package com.banksystem.BankSystem.entities.bankaccounts;

import jakarta.persistence.Column;

public class SavingBankAccount extends BankAccount{

    @Column
    private double monthlySaveAmount;

    @Column
    private boolean isActive;


}
