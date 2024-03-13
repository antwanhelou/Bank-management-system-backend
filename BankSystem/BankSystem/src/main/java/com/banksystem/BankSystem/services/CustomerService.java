package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.BaseUserRepository;
import com.banksystem.BankSystem.repository.CustomerRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService extends BaseUserService<Customer> {



    @Autowired
    private CustomerRepository customerRepository;



    public ResponseEntity<Map<String, String>> addCustomer(UserCredentialsDTO userCredentialsDTO) {
        Customer customer = Customer.builder().build();
        return this.registerUser(userCredentialsDTO, customer);
    }
//    public Customer updateCustomer(UUID id, Customer customerDetails) {
//    }
    @Transactional
    public Customer getCustomerWithAccounts(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + customerId));
        customer.getBankAccounts().size(); // Access the accounts to trigger their loading
        return customer;
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));
        customerRepository.delete(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
//    public Optional<Customer> getCustomer(UUID id) {
//        return customerRepository.findById(id);
//    }

    @Override
    protected BaseUserRepository<Customer> getUserRepository() {
        return customerRepository;
    }

    public ResponseEntity<CustomerDTO> getCustomer(final UUID id) throws UserNotFoundException {
        final Customer customer = getUser(id);
        CustomerDTO customerDTO = CustomerDTO.builder().build();
        customerDTO.set(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }
}
