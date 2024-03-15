package com.banksystem.BankSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RepaymentDTO {
    private BigDecimal repaymentAmount;
    private UUID accountId;
    private UUID loanId;
}
