package com.banksystem.BankSystem.entities.transactions;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", parameters = {
            @org.hibernate.annotations.Parameter (name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    @Column(nullable = false, name = "transaction_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccount bankAccount;

    @Column(nullable = false)
    private Instant transactionDate;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String description;

    @Column
    private String currency;
}

