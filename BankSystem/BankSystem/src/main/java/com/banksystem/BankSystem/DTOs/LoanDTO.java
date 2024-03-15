package com.banksystem.BankSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanDTO {
    private BigDecimal amount;
    private UUID accountId;
    private boolean isPaid;
    private BigDecimal interestRate;
    private LocalDate dueDate;

}