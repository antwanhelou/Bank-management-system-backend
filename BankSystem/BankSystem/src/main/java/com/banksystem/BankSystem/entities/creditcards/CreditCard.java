package com.banksystem.BankSystem.entities.creditcards;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Data
@Entity

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)

public abstract class CreditCard {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2") @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private long creditLimit;

    @ManyToOne
    @JoinColumn(
            name = "account_id"
    )
    private BankAccount bankAccount;




}
