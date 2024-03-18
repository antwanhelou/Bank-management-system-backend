package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.enums.Currency;
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
public class DepositOrWithdrawDTO {

    private UUID customerID;

    private String bankAccountNumber;

    private BigDecimal amount;

    private Currency currency;

}
