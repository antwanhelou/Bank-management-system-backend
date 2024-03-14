package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.services.CustomerService;
import com.banksystem.BankSystem.exceptions.CustomerAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Map<String, String>> createCustomer(@RequestBody final UserCredentialsDTO userCredentials) throws CustomerAlreadyExists {
        return customerService.addCustomer(userCredentials);
    }

    @PostMapping("/updateCustomerDetails")
    public ResponseEntity<Map<String, String>> updateCustomerDetails(@RequestBody final CustomerDTO baseUserDTO) throws UserNotFoundException {
        return customerService.updateCustomerDetails(baseUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable("id") final UUID id) throws UserNotFoundException {
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") final UUID id) throws UserNotFoundException {
        return customerService.getCustomer(id);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<Iterable<CustomerDTO>> getAllCustomers(){
        return customerService.getAllCustomer();
    }





}
