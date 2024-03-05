package com.banksystem.BankSystem.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name="Accounts")
public class Account {

    @Id
    @Column
    @GeneratedValue
    private Integer id;
    @Column
    private String accountNumber;

    //  private String type;
    @Column
     private String status;
    @Column
    private double balance;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "accounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers = new HashSet<>();
}