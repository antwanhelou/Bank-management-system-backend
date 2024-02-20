package com.banksystem.BankSystem.entities;


import jakarta.persistence.Entity;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
public class Loan {

    private UUID id;
    private float amount;
    private float interestRate;
    private String repaymentStatus;
}
