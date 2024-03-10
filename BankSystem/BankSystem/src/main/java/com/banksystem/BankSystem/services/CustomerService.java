package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.repository.CustomerRepository;
import com.banksystem.BankSystem.exceptions.CustomerAlreadyExists;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {



    @Autowired
    private CustomerRepository customerRepository;



    @Transactional
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExists {

        if(customer.getId()!=null && this.customerRepository.existsById(customer.getId()))
        {
            throw new CustomerAlreadyExists("Author with id "+customer.getId()+" already exists");
        }


        return this.customerRepository.save(customer);
    }
    @Transactional
    public Customer updateCustomer(UUID id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setAddress(customerDetails.getAddress());
        return customerRepository.save(customer);
    }
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
    public Optional<Customer> getCustomer(UUID id) {
        return customerRepository.findById(id);
    }
}
