//package com.banksystem.BankSystem.controllers;
//
//
//import com.banksystem.BankSystem.DTOs.BaseUserDTO;
//import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
//import com.banksystem.BankSystem.entities.users.BaseUser;
//import com.banksystem.BankSystem.entities.users.Customer;
//import com.banksystem.BankSystem.entities.users.UserCredentials;
//import com.banksystem.BankSystem.exceptions.CustomerAlreadyExists;
//import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
//import com.banksystem.BankSystem.services.RegistrationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Map;
//
//@Controller
//@RequestMapping("/api/register")
//public class RegistrationController {
//@Autowired
//private RegistrationService registrationService;
//    @PostMapping("/addCustomer")
//    public ResponseEntity<UserCredentials> registerUser(@RequestBody UserCredentialsDTO userCredentialsDTO) {
//        try {
//
//            // Assuming you have a method to convert DTO to Entity and create a BaseUser from part of the DTO
//            //BaseUser baseUser = createBaseUserFromDTO(userCredentialsDTO); // Implement this method based on your requirements
//            UserCredentials registeredUser = registrationService.registerUser(userCredentialsDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
//        } catch (UserNameAlreadyExistsException e) {
//            // Handle the case where the username already exists
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Consider a more descriptive error response
//        }
//    }
//    private UserCredentials createBaseUserFromDTO(UserCredentialsDTO dto) {
//        UserCredentials customer = new UserCredentials();
//        customer.setUserName(dto.getUserName());
//        customer.setPassword(dto.getPassword());
//        customer.setEmail(dto.getEmail());
//        return customer;
//    }
//}
