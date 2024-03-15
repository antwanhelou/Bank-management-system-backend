package com.banksystem.BankSystem.entities.schedule;


import com.banksystem.BankSystem.entities.users.Administrator;
import com.banksystem.BankSystem.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")

public class Task {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false, name = "task_id")
    private UUID id;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant creationTime;

    @Column(nullable = false)
    private Instant deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Administrator assignedAdministrator;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;



}
