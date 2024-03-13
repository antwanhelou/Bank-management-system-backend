package com.banksystem.BankSystem.DTOs;

import lombok.Data;

@Data
public class RegistrationDTO {
    private UserCredentialsDTO userCredentials;
    private BaseUserDTO baseUser;

    // Getters and setters
}
