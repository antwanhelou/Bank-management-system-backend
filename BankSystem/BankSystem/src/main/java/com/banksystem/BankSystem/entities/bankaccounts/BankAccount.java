package com.banksystem.BankSystem.entities.bankaccounts;

import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import com.banksystem.BankSystem.entities.users.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name="bank_accounts")
public abstract class BankAccount {

    public static double defaultBalance = 1000;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", parameters = {
            @org.hibernate.annotations.Parameter (name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    @Column(nullable = false, name = "account_id")
    private  UUID id;

    @Column
    private String accountNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double minimumBalance;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String branchCode;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "accounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Customer> customers;


    @OneToMany(mappedBy = "bankAccount")
    private List<CreditCard> creditCardList;


    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactions;

}