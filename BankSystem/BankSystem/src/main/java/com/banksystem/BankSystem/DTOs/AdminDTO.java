package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.users.Admin;
import com.banksystem.BankSystem.enums.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminDTO extends BaseUserDTO{


    private AdminRole adminRole;


    public void set(Admin admin){
        super.set(admin);
        this.adminRole = admin.getAdminRole();

    }
}
