package com.banksystem.BankSystem.entities.users;


import com.banksystem.BankSystem.enums.AdminRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")

public class Admin extends BaseUser{


    @Column
    private AdminRole adminRole;

}
