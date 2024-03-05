package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.Customer;
import com.banksystem.BankSystem.repository.customerRepository;
import exceptions.CustomerAlreadyExists;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class customerService {



    @Autowired
    private customerRepository customerRepo;



    @Transactional
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExists {

        if(customer.getId()!=null && this.customerRepo.existsById(customer.getId()))
        {
            throw new CustomerAlreadyExists("Author with id "+customer.getId()+" already exists");
        }


        return this.customerRepo.save(customer);
    }
    @Transactional
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setAddress(customerDetails.getAddress());
        return customerRepo.save(customer);
    }
    @Transactional
    public Customer getCustomerWithAccounts(Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + customerId));
        customer.getAccounts().size(); // Access the accounts to trigger their loading
        return customer;
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + id));
        customerRepo.delete(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }
    public Optional<Customer> getCustomer(Integer id) {
        return customerRepo.findById(id);
    }
}
