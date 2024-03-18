package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;

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
