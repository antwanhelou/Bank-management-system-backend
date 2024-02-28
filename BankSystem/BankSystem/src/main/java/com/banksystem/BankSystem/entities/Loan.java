package com.banksystem.BankSystem.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
@Builder
@Table(name="Loans")
public class Loan {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private float amount;
    //private float interestRate;
    //private String repaymentStatus;
    @ManyToOne
    private Account account;
}
