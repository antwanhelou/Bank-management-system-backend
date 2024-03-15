package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.NewUserCredentialsDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.exceptions.CustomerAlreadyExists;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;
import com.banksystem.BankSystem.services.AdministratorService;
import com.banksystem.BankSystem.services.CustomerService;
import com.banksystem.BankSystem.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/register")
public class UserRegistrationController {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final AdministratorService administratorService;

    @Autowired
    private final RegistrationService registrationService;

    public UserRegistrationController(CustomerService customerService, AdministratorService administratorService, RegistrationService registrationService) {
        this.customerService = customerService;
        this.administratorService = administratorService;
        this.registrationService = registrationService;
    }


    @PostMapping("/registerCustomer")
    public ResponseEntity<Map<String, String>> createCustomer(@RequestBody final UserCredentialsDTO userCredentials) throws CustomerAlreadyExists {
        return customerService.addCustomer(userCredentials);
    }

    @PostMapping("/registerAdministrator")
    public ResponseEntity<Map<String, String>> createAdmin(@RequestBody final UserCredentialsDTO userCredentials) throws CustomerAlreadyExists {
        return administratorService.addAdmin(userCredentials);
    }

    @PostMapping("/updateCredentials")
    public ResponseEntity<Map<String, String>> updateCredentials(@RequestBody final NewUserCredentialsDTO newUserCredentialsDTO) throws ResetCredentialsException {
        return registrationService.updateUserCredentials(newUserCredentialsDTO);
    }

}
