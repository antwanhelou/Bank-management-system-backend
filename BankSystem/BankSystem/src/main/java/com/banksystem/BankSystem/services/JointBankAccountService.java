package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.CustomerRepository;
import com.banksystem.BankSystem.repository.JointBankAccountRepository;
import com.banksystem.BankSystem.utilities.Constants;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JointBankAccountService extends BankAccountService<JointBankAccount> {

    @Autowired
    private final JointBankAccountRepository jointBankAccountRepository;


    public JointBankAccountService(JointBankAccountRepository jointBankAccountRepository) {
        this.jointBankAccountRepository = jointBankAccountRepository;
    }


    @Override
    public ResponseEntity<Map<String, String>> closeAccount(final UUID accountID) throws BankAccountNotFoundException, CloseAccountException {
        JointBankAccount account = this.findBankAccount(accountID);
        if(account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0){
            throw new CloseAccountException("Cannot Close Account, You need to withdraw your balance");
        }else if(account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0){
            throw new CloseAccountException("Cannot Close Account, You have debts!");
        }
        if(!account.getAssociatedBankAccounts().isEmpty()){
            throw new CloseAccountException("Cannot Close Account, You still have open save accounts, Please close them first!");
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);

    }

    @Override
    public BankAccountRepository<JointBankAccount> getRepository() {
        return this.jointBankAccountRepository;
    }

    @Override
    public ResponseEntity<BankAccountDTO> createBankAccount(final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        JointBankAccount jointBankAccount = JointBankAccount.builder().build();
        this.completeBankAccountDetails(jointBankAccount, requestDTO);
        List<UUID> jointCustomersIDs = requestDTO.getCustomersIDs();
        jointBankAccount.setCustomers(new HashSet<>());
        for(Customer customer: customerService.findCustomersByIDs(jointCustomersIDs)){
            jointBankAccount.addCustomer(customer);
            customerService.saveCustomer(customer);
        }
        jointBankAccount.setMaximumDailyWithdrawal(new BigDecimal(Constants.MAXIMAL_DAILY_WITHDRAWAL_PER_CUSTOMER));
        JointBankAccountDTO bankAccountDTO = new JointBankAccountDTO();
        bankAccountDTO.set(jointBankAccount);
        return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
    }

    /*@Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) {
        return null;
    }*/

}
