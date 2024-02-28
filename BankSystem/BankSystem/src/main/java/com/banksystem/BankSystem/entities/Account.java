package com.banksystem.BankSystem.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name="Accounts")
public class Account {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private String accountNumber;

    //  private String type;
   // private String status;
    private float balance;

    @ManyToMany
    private List<Customer> customer;
}