package com.banksystem.BankSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepositDTO {

    private UUID customerID;

    private UUID bankAccountID;

    private BigDecimal amount;

    private String currency;

}
