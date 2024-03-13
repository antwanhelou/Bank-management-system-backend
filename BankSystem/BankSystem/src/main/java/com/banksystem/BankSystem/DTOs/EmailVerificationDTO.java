package com.banksystem.BankSystem.DTOs;


import lombok.Data;

@Data
public class EmailVerificationDTO {
    private String email;
    private String verificationCode;

}
