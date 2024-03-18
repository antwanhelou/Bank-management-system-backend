package com.banksystem.BankSystem.entities.transactions;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@EqualsAndHashCode(of = {"description"})
@Builder
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false, name = "transaction_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "to_bank_account", nullable = false)
    private BankAccount toBankAccount;

    @ManyToOne
    @JoinColumn(name = "from_bank_account")
    private BankAccount fromBankAccount;


    @Column(nullable = false)
    private Instant transactionDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column
    private UUID customerID;

}

