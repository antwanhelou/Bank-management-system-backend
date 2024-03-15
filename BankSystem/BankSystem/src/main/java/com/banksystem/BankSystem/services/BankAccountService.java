package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.users.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import com.banksystem.BankSystem.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public abstract class BankAccountService<T extends BankAccount> {


//    public ResponseEntity<>

    protected abstract BankAccountRepository<T> getRepository();


    public String generateUniqueRandomBankAccountNumber(){
        String characters = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        Optional<BankAccount> bankAccounts = this.getRepository().findByBankAccountNumber("aa");
        // Generate 12 random characters and append them to the StringBuilder
        for (int i = 0; i < 13; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();

    }
}
