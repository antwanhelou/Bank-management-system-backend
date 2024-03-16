package com.banksystem.BankSystem.entities.bankaccounts;

import com.banksystem.BankSystem.entities.users.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "joint_bank_accounts")

public class JointBankAccount extends BankAccount{

    @Column
    private BigDecimal maximumDailyWithdrawal;

    @Column
    private Set<UUID> associatedBankAccounts;

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

}
