package com.banksystem.BankSystem.DTOs;


import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import com.banksystem.BankSystem.enums.AccountType;
import com.banksystem.BankSystem.enums.CreditCardType;
import com.banksystem.BankSystem.enums.RequestStatus;
import com.banksystem.BankSystem.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerRequestDTO {

    private UUID id;


    private UUID customerID;

    private RequestType requestType;

    private AccountType accountType;

    private CreditCardType creditCardType;

    private RequestStatus requestStatus;

    private CustomerDTO customerDTO;

    private String title;

    private Instant creationTime;


    public void set(CustomerRequest customerRequest){
        this.id = customerRequest.getId();
        this.requestType = customerRequest.getRequestType();
        this.accountType = customerRequest.getAccountType();
        this.requestStatus = customerRequest.getStatus();
        this.creditCardType = customerRequest.getCreditCardType();
        this.customerDTO = CustomerDTO.builder().build();
        this.customerDTO.set(customerRequest.getCustomer());
        this.title = customerRequest.getTitle();
        this.creationTime = customerRequest.getCreationTime();
    }

}
