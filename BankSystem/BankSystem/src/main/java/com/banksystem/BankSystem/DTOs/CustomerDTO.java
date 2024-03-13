package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.users.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO extends BaseUserDTO{



    private List<BankAccountDTO> bankAccountDTOs;


    private void set(Customer customer){
        super.set(customer);
        this.bankAccountDTOs = new ArrayList<>();
        for(BankAccount bankAccount: customer.getBankAccounts()){
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.set(bankAccount);
            this.bankAccountDTOs.add(bankAccountDTO);
        }

    }
}
