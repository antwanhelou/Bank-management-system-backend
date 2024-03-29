package com.banksystem.BankSystem.entities.users;


import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseUser{

    @Column(name = "customer_id")
    private UUID id;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Set<CustomerRequest> customerRequest;


    @JsonIgnoreProperties("customers")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_accounts",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<BankAccount> bankAccounts = new HashSet<>();
    public double getTotalBalance() {
        return 0;
//        return this.bankAccounts.stream()
//                .mapToDouble(BankAccount::getBalance) // Assuming BankAccount has a getBalance method
//                .sum();
    }

}
