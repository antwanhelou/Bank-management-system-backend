package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller

@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomerDetails")
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
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }

    @PostMapping("/requestBankAccount")
    public ResponseEntity<Map<String, String>> requestBankAccount(@RequestBody BankAccountDTO bankAccountDTO){
        return customerService.requestBankAccount(bankAccountDTO);

    }

}
