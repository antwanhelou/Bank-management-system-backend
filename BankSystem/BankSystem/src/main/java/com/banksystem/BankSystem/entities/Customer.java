package com.banksystem.BankSystem.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "Customers")
@Builder
public class Customer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String email;

    private String address;

    @ManyToMany(mappedBy = "customer")
    private List<Account> accounts;
}
