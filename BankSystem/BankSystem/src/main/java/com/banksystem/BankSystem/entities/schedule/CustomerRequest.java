package com.banksystem.BankSystem.entities.schedule;


import com.banksystem.BankSystem.DTOs.CustomerRequestDTO;
import com.banksystem.BankSystem.entities.users.BaseUser;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountType;
import com.banksystem.BankSystem.enums.CreditCardType;
import com.banksystem.BankSystem.enums.RequestStatus;
import com.banksystem.BankSystem.enums.RequestType;
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
@Table(name = "customer_requests")
@Builder
public class CustomerRequest {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false, name = "request_id")
    private UUID id;

    @Column
    private String title;

    @Column
    private Instant creationTime;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
