package com.banksystem.BankSystem.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Account {


    private UUID id;
    private String accountNumber;

  //  private String type;
    private String status;
    private float balance;
}