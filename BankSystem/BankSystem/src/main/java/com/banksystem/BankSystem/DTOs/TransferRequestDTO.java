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
public class TransferRequestDTO {

    private UUID customerID;

    private UUID fromBankAccount;

    private UUID toBankAccount;

    private BigDecimal amount;


}
