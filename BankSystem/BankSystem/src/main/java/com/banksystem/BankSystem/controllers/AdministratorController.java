package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.AdministratorDTO;
import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;



    @PostMapping("/updateAdministratorDetails")
    public ResponseEntity<Map<String, String>> updateAdministratorDetails(@RequestBody final CustomerDTO baseUserDTO) throws UserNotFoundException {
        return administratorService.updateAdministratorDetails(baseUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAdministrator(@PathVariable("id") final UUID id) throws UserNotFoundException {
        return administratorService.deleteAdministrator(id);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable("id") final UUID id) throws UserNotFoundException {
        return administratorService.getAdministrator(id);
    }

    @GetMapping("/getAllAdministrators")
    public ResponseEntity<Iterable<AdministratorDTO>> getAllAdministrators(){
        return administratorService.getAllAdministrators();
    }



}
