package com.banksystem.BankSystem.entities.loans;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
@Builder
public class Loan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;

    @Column
    private boolean isPaid = false;

    @Column
    private BigDecimal amount; // The initial loan amount

    @Column
    private BigDecimal interestRate; // Yearly interest rate

    @Column
    private int term; // Duration of the loan in months

    @Column
    private BigDecimal monthlyRepaymentAmount; // You might store this if it's fixed for all installments

    @Column
    private LocalDate dueDate;

    @Column
    private BigDecimal repaid;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;


    // Constructors, Getters, and Setters
}
