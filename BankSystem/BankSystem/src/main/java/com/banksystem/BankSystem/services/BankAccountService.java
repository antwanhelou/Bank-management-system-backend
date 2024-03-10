package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.users.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import com.banksystem.BankSystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private  CustomerRepository customerRepository;


    public BankAccount createAccountForCustomer(BankAccount bankAccount, UUID customerId) {
        // Fetch the customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + customerId));

        // Set the relationship
        bankAccount.getCustomers().add(customer); // Make sure Account's getCustomers() is initialized properly
        customer.getBankAccounts().add(bankAccount); // Ensure bi-directional consistency

        // Save the account, which should cascade the relationship update if cascading is configured
        return bankAccountRepository.save(bankAccount);
    }


    public String generateUniqueRandomBankAccountNumber(){
        String characters = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        Optional<BankAccount> bankAccounts = bankAccountRepository.findByBankAccountNumber("aa");
        // Generate 12 random characters and append them to the StringBuilder
        for (int i = 0; i < 13; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();

    }
}
