package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.users.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseUserDTO {

    private UUID id;

    private String name;

    private String email;

    private String address;

    private boolean isVerified;


    public void set(BaseUser baseUser){
        this.id = baseUser.getId();
        this.name = baseUser.getName();
        this.email = baseUser.getUserCredentials().getEmail();
        this.address = baseUser.getAddress();
        this.isVerified = baseUser.isVerified();
    }

}
