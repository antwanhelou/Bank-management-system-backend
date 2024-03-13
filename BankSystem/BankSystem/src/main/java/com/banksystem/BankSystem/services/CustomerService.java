package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.CustomerDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
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


    public ResponseEntity<Map<String, String>> addCustomer(final UserCredentialsDTO userCredentialsDTO) {
        final Customer customer = Customer.builder().build();
        return this.registerUser(userCredentialsDTO, customer);
    }


    public ResponseEntity<Map<String, String>> updateCustomer(final BaseUserDTO baseUserDTO) throws UserNotFoundException {
        return updateBaseUserDetails(baseUserDTO);
    }

    public ResponseEntity<Map<String, String>> deleteCustomer(final BaseUserDTO baseUserDTO) throws UserNotFoundException {
        return deleteBaseUser(baseUserDTO);
    }

    public ResponseEntity<CustomerDTO> getCustomer(final UUID customerID) throws UserNotFoundException {
        Customer customer = getUser(customerID);
        CustomerDTO customerDTO = CustomerDTO.builder().build();
        customerDTO.set(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    public ResponseEntity<Iterable<CustomerDTO>> getAllCustomer(){
        Iterable<Customer> customers = this.getAllUsers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer: customers){
            CustomerDTO customerDTO = CustomerDTO.builder().build();
            customerDTO.set(customer);
            customerDTOS.add(customerDTO);
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
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

}
