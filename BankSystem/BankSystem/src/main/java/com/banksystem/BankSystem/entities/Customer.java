package com.banksystem.BankSystem.entities;


import jakarta.persistence.Entity;
import lombok.Data;
import java.util.UUID;
@Entity
@Data

public class Customer {

    private UUID id;

    private String name;

    private String email;

    private String address;
}
