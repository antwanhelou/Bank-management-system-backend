package com.banksystem.BankSystem.entities.users;


import com.banksystem.BankSystem.enums.AdminRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")
@Builder
public class Admin extends BaseUser{


    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;

}
