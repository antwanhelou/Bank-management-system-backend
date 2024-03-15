package com.banksystem.BankSystem.entities.bankaccounts;

import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "accountNumber", "balance"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BankAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false, name = "account_id")
    private  UUID id;

    @Column
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double minimumBalance;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String branchCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer owner;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "bankAccounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers;

    @OneToMany(mappedBy = "bankAccount")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private Set<Loan> loans = new HashSet<>();
    private void init(){
        customers = new HashSet<>();
        transactions = new HashSet<>();
    }

}