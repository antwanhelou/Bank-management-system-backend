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

import java.math.BigDecimal;
import java.util.*;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "accountNumber", "balance"})
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal minimumBalance;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String branchCode;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer owner;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "bankAccounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected Set<Customer> customers;

    @OneToMany(mappedBy = "fromBankAccount")
    private Set<Transaction> fromBankAccountTransactions;

    @OneToMany(mappedBy = "toBankAccount")
    private Set<Transaction> toBankAccountTransactions;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private Set<Loan> loans;


    public void addLoan(Loan loan){
        this.loans.add(loan);
    }

    public void addFromBankAccountTransaction(Transaction transaction){
        this.fromBankAccountTransactions.add(transaction);
    }

    public void addToBankAccountTransaction(Transaction transaction){
        this.toBankAccountTransactions.add(transaction);
    }


    public void init(){
        fromBankAccountTransactions = new HashSet<>();
        toBankAccountTransactions = new HashSet<>();
        loans = new HashSet<>();
    }

}