package com.banksystem.BankSystem.entities.bankaccounts;

import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name="bank_accounts")
@EqualsAndHashCode(of = {"id"})
public abstract class BankAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.uuid.UuidGenerator.class)
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "bankAccounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers;


    @OneToMany(mappedBy = "bankAccount")
    private Set<CreditCard> creditCards;


    @OneToMany(mappedBy = "bankAccount")
    private Set<Transaction> transactions;


    private void init(){
        customers = new HashSet<>();
        creditCards = new HashSet<>();
        transactions = new HashSet<>();
    }

}