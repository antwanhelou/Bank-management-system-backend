package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.entities.users.UserCredentials;
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
    public ResponseEntity<Map<String, String>> createCustomer(@RequestBody UserCredentialsDTO userCredentials) throws CustomerAlreadyExists {
        return customerService.addCustomer(userCredentials);
    }


//    @GetMapping("/getCustomer/{id}")
//    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") UUID id) {
//        Customer customer = customerService.getCustomer(id)
//                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));
//        return ResponseEntity.ok().body(customer);
//    }

//    @GetMapping("/getAll")
//    public List<Customer> getAllCustomers() {
//        return customerService.getAllCustomers();
//    }

//    @PutMapping("/updateCustomer/{id}")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") UUID id, @RequestBody Customer customerDetails) {
//        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
//        return ResponseEntity.ok(updatedCustomer);
//    }

//    @DeleteMapping("/deleteCustomer/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "id") UUID id) {
//        customerService.deleteCustomer(id);
//        return ResponseEntity.ok().build();
//    }
}
