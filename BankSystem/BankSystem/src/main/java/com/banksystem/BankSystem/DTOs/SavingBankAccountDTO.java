package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class SavingBankAccountDTO extends BankAccountDTO{

    private BigDecimal monthlySaveAmount;

    private boolean isActive;

    public void set(SavingBankAccount savingBankAccount){
        super.set(savingBankAccount);
        this.monthlySaveAmount = savingBankAccount.getMonthlySaveAmount();
    }

}
// Deposit!