package com.banksystem.BankSystem.entities.creditcards;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Data
@Entity

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = {"id"})

public abstract class CreditCard {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private double creditLimit;

    @ManyToOne
    @JoinColumn(
            name = "account_id"
    )
    private BankAccount bankAccount;




}
