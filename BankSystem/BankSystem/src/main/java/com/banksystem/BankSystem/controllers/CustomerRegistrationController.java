package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
import com.banksystem.BankSystem.services.CustomerService;
import com.banksystem.BankSystem.exceptions.CustomerAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/register/customer")
public class CustomerRegistrationController {
    @Autowired
    private final CustomerService customerService;

    @Autowired
    public CustomerRegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Map<String, String>> createCustomer(@RequestBody final UserCredentialsDTO userCredentials) throws CustomerAlreadyExists, UserNameAlreadyExistsException {
        return customerService.addCustomer(userCredentials);
    }



}
