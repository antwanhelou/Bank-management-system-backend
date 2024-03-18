package com.banksystem.BankSystem.entities.schedule;


import com.banksystem.BankSystem.entities.users.Administrator;
import com.banksystem.BankSystem.enums.TaskPriority;
import com.banksystem.BankSystem.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Task implements Comparable<Task>{

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

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @OneToOne(mappedBy = "task", cascade = CascadeType.DETACH)
    private CustomerRequest customerRequest;

    @Override
    public int compareTo(Task o) {
        int comparePriorityValue = this.taskPriority.compareTo(o.taskPriority);
        return (comparePriorityValue != 0) ? comparePriorityValue : this.deadline.compareTo(o.deadline);
    }
}
