package com.banksystem.BankSystem.DTOs;

import com.banksystem.BankSystem.entities.users.Administrator;
import com.banksystem.BankSystem.enums.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdministratorDTO extends BaseUserDTO{


    private AdminRole adminRole;


    public void set(Administrator administrator){
        super.set(administrator);
        this.adminRole = administrator.getAdminRole();

    }
}
