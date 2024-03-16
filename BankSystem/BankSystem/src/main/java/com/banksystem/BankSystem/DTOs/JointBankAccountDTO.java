package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class JointBankAccountDTO extends BankAccountDTO{

    private BigDecimal maximumDailyWithdrawal;


    public void set(JointBankAccount jointBankAccount) {
        super.set(jointBankAccount);
        this.maximumDailyWithdrawal = jointBankAccount.getMaximumDailyWithdrawal();

    }


}
