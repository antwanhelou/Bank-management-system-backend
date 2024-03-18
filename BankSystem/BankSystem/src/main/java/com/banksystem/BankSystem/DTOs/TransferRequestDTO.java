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
public class TransferRequestDTO {

    private UUID customerID;

    private String fromBankAccount;

    private String toBankAccount;

    private BigDecimal amount;



}
