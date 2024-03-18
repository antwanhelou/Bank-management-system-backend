package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.CustomerRequestDTO;
import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.RequestStatus;
import com.banksystem.BankSystem.exceptions.object_not_found.EntityNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.RequestNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.TaskNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.CustomerRequestRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRequestService {

    @Autowired
    private CustomerRequestRepository customerRequestRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public ResponseEntity<Map<String, String>> submitRequest(CustomerRequestDTO request) throws UserNotFoundException {
        CustomerRequest customerRequest = processRequest(request);
        this.saveRequest(customerRequest);
        return taskService.createTask(customerRequest);
    }

    private CustomerRequest processRequest(CustomerRequestDTO request) throws UserNotFoundException {
        Customer customer =  customerService.getUser(request.getCustomerID());
        CustomerRequest customerRequest = CustomerRequest.builder().build();
        customerRequest.setRequestType(request.getRequestType());
        customerRequest.setCreationTime(request.getCreationTime());
        customerRequest.setAccountType(request.getAccountType());
        customerRequest.setCreditCardType(request.getCreditCardType());
        customerRequest.setTitle(request.getTitle());
        customerRequest.setCustomer(customer);
        customerRequest.setCreationTime(Instant.now());
        customerRequest.setStatus(RequestStatus.IN_PROGRESS);
        return customerRequest;
    }

    public ResponseEntity<Map<String, String>> approveRequest(final UUID requestID) throws EntityNotFoundException {
        CustomerRequest customerRequest = getRequest(requestID);
        customerRequest.setStatus(RequestStatus.APPROVED);
        this.saveRequest(customerRequest);
        taskService.taskDone(customerRequest.getTask());
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> declineRequest(final UUID requestID) throws EntityNotFoundException {
        CustomerRequest customerRequest = getRequest(requestID);
        customerRequest.setStatus(RequestStatus.DECLINED);
        this.saveRequest(customerRequest);
        taskService.taskDone(customerRequest.getTask());
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);

    }

    public CustomerRequest getRequest(final UUID requestID) throws RequestNotFoundException {
        Optional<CustomerRequest> searchResult = this.customerRequestRepository.findById(requestID);
        if(searchResult.isEmpty()){
            throw new RequestNotFoundException("Cannot find Request with ID: " +requestID);
        }
        return searchResult.get();
    }

    public void saveRequest(final CustomerRequest request){
        this.customerRequestRepository.save(request);
    }

}
