package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CreateBankAccountRequestDTO {

    private UUID ownerID;

    private BigDecimal minimumBalance;

    private List<UUID> customersIDs;

    private AccountType accountType;

    private String associatedBankAccountNumber;

}
