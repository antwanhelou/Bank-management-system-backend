package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.EmailVerificationDTO;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.repository.UserCredentialsRepository;
import com.banksystem.BankSystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public class EmailController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService; // Assuming this service is already implemented




//    @Scheduled(cron = "0 0 0 1 * ?")
//    public void sendBalanceWarningEmails() {
//        List<Customer> customers = customerService.getAllCustomer();
//        customers.stream()
//                .filter(customer -> customer.getTotalBalance() <= -1000)
//                .forEach(customer -> {
//                    String email = customer.getEmail(); // Assuming Customer has an email field
//                    emailService.sendBalanceMessage(
//                            email,
//                            "Balance Alert",
//                            "Dear " + customer.getName() + ", you are in a minus. Your balance is less than -1000."
//                    );
//                });
//    }

}
