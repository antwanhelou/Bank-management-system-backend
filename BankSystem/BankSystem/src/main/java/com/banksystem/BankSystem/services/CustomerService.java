package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
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

import java.util.*;

@Service
public class CustomerService extends BaseUserService<Customer> {



    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;


    public ResponseEntity<Map<String, String>> addCustomer(UserCredentialsDTO userCredentialsDTO) {
        Customer customer = Customer.builder().build();
        emailService.sendWelcomeMessage(Customer.builder().build().getEmail(), "Welcome!","welcome to antwan and hussam");
        return this.registerUser(userCredentialsDTO, customer);
    }

    public ResponseEntity<Map<String, String>> updateCustomerDetails(CustomerDTO baseUserDTO) throws UserNotFoundException {
        return updateBaseUserDetails(baseUserDTO);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> deleteCustomer(UUID id) throws UserNotFoundException {
        return deleteBaseUser(id);
    }

    public Iterable<CustomerDTO> getAllCustomer(){
        Iterable<Customer> customers = this.getAllUsers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer: customers){
            CustomerDTO customerDTO = CustomerDTO.builder().build();
            customerDTO.set(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public ResponseEntity<Iterable<BankAccountDTO>> getCustomerBankAccounts(final UUID customerID) throws UserNotFoundException {
        Optional<Customer> searchResult = this.getUserRepository().findById(customerID);
        if(searchResult.isEmpty()){
            throw new UserNotFoundException("Provided Customer ID does not match any customer");
        }
        Customer customer = searchResult.get();
        List<BankAccountDTO> bankAccountDTOS = new ArrayList<>();
        for(BankAccount bankAccount: customer.getBankAccounts()){
            BankAccountDTO bankAccountDTO = BankAccountDTO.builder().build();
            bankAccountDTO.set(bankAccount);
            bankAccountDTOS.add(bankAccountDTO);
        }

        return new ResponseEntity<>(bankAccountDTOS, HttpStatus.OK);


    }

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

    public ResponseEntity<Map<String, String>> requestBankAccount(BankAccountDTO bankAccountDTO){

        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }
}
