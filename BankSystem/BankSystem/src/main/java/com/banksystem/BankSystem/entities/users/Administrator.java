package com.banksystem.BankSystem.entities.users;


import com.banksystem.BankSystem.entities.schedule.Task;
import com.banksystem.BankSystem.enums.AdminRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")
@Builder
public class Administrator extends BaseUser{


    @Column(name = "admin_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;

    @Column
    private boolean isAuthorized;

    @OneToMany(mappedBy = "assignedAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Column
    private Set<Task> tasks;


}
