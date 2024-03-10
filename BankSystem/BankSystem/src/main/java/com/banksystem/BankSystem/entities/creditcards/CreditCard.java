package com.banksystem.BankSystem.entities.creditcards;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

public abstract class CreditCard {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", parameters = {
            @org.hibernate.annotations.Parameter (name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    @Column(nullable = false)
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
