package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.entities.Customer;
import com.banksystem.BankSystem.services.customerService;
import exceptions.CustomerAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private final customerService customerService;

    @Autowired
    public CustomerController(customerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public Customer createCustomer(@RequestBody Customer customer) throws CustomerAlreadyExists {
        return customerService.addCustomer(customer);
    }


    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id) {
        Customer customer = customerService.getCustomer(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Integer id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "id") Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
