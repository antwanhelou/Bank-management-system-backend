package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.users.UserCredentials;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserCredentialsDTO {
    private String userName;
    private String password;
    private String email;

}
