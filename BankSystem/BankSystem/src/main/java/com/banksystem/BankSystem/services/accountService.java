package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.Account;
import com.banksystem.BankSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.accountRepository;

import com.banksystem.BankSystem.repository.customerRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class accountService {

    @Autowired
    private accountRepository accountRepository;
    @Autowired
    private  customerRepository customerRepository;





    public Account createAccountForCustomer(Account account, Integer customerId) {
        // Fetch the customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + customerId));

        // Set the relationship
        account.getCustomers().add(customer); // Make sure Account's getCustomers() is initialized properly
        customer.getAccounts().add(account); // Ensure bi-directional consistency

        // Save the account, which should cascade the relationship update if cascading is configured
        return accountRepository.save(account);
    }
}
