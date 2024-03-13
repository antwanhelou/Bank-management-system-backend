package com.banksystem.BankSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewUserCredentialsDTO {

    private String userName;
    private String oldPassword;
    private String newPassword;

}
