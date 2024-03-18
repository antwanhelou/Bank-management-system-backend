package com.banksystem.BankSystem.controllers;


import com.banksystem.BankSystem.DTOs.CustomerRequestDTO;
import com.banksystem.BankSystem.exceptions.object_not_found.EntityNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.RequestNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.TaskNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.services.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private CustomerRequestService customerRequestService;


    @PostMapping("/submitRequest")
    public ResponseEntity<Map<String, String>> submitRequest(CustomerRequestDTO request) throws UserNotFoundException {
        return customerRequestService.submitRequest(request);
    }

    @PostMapping("/approveRequest/{requestID}")
    public ResponseEntity<Map<String, String>> approveRequest(@PathVariable final UUID requestID) throws EntityNotFoundException {
        return customerRequestService.approveRequest(requestID);

    }

    @PostMapping("/declineRequest/{requestID}")
    public ResponseEntity<Map<String,String>> declineRequest(@PathVariable final UUID requestID) throws EntityNotFoundException {
        return customerRequestService.declineRequest(requestID);
    }

}
