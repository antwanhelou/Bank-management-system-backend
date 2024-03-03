package com.banksystem.BankSystem.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name="Accounts")
public class Account {

    @Id
    @Column
    @GeneratedValue
    private Integer id;
    @Column
    private String accountNumber;

    //  private String type;
    // private String status;
    @Column
    private float balance;

    @ManyToMany(mappedBy = "accounts")
    private Set<Customer> customers = new HashSet<>();
}