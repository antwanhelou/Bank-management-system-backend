package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.creditcards.CreditCard;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@EqualsAndHashCode(callSuper = false)

public class IndividualBankAccountDTO extends BankAccountDTO{

    private List<CreditCardDTO> creditCardDTOs;

    public void set(IndividualBankAccount individualBankAccount){
        super.set(individualBankAccount);
        this.creditCardDTOs = new ArrayList<>();
        for(CreditCard creditCard: individualBankAccount.getCreditCards()){
            CreditCardDTO creditCardDTO = new CreditCardDTO();
            creditCardDTO.set(creditCard);
            this.creditCardDTOs.add(creditCardDTO);
        }

    }

}
