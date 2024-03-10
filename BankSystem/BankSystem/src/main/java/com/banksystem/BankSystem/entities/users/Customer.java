package com.banksystem.BankSystem.entities.users;


import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor

public class Customer extends BaseUser{


//    @Column(nullable = false)
//    private long customerID;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_accounts",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<BankAccount> bankAccounts = new HashSet<>();
}
