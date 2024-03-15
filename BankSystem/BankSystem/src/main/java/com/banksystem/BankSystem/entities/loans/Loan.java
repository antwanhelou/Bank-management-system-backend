package com.banksystem.BankSystem.entities.loans;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Loan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;
    private boolean isPaid = false;
    private BigDecimal amount; // The initial loan amount
    private BigDecimal interestRate; // Yearly interest rate
    private int term; // Duration of the loan in months
    private BigDecimal monthlyRepaymentAmount; // You might store this if it's fixed for all installments
    private LocalDate dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;


    // Constructors, Getters, and Setters
}
