package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.TransactionType;
import com.banksystem.BankSystem.exceptions.*;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.JointBankAccountRepository;
import com.banksystem.BankSystem.utilities.Constants;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class JointBankAccountService extends BankAccountService<JointBankAccount> {

    @Autowired
    private final JointBankAccountRepository jointBankAccountRepository;


    public JointBankAccountService(JointBankAccountRepository jointBankAccountRepository) {
        this.jointBankAccountRepository = jointBankAccountRepository;
    }


    @Override
    public ResponseEntity<Map<String, String>> closeAccount(final String accountNumber) throws BankAccountNotFoundException, CloseAccountException {
        JointBankAccount account = this.findBankAccount(accountNumber);
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

    @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) throws BankSystemException {
        final String depositBankID = request.getToBankAccount();
        final String withdrawalBankID = request.getFromBankAccount();
        final BigDecimal amount = request.getAmount();
        JointBankAccount withdrawalBank = this.findBankAccount(withdrawalBankID);
        Optional<BankAccount> depositBankSearchResult = this.getRepository().findByAccountNumberAllTables(depositBankID);
        if(depositBankSearchResult.isEmpty()){
            throw new BankAccountNotFoundException("Cannot find Bank Account with number : " + depositBankID);
        }
        BankAccount depositBankAccount = depositBankSearchResult.get();
        if(withdrawalBank.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("You Do Not Have The Desired Transfer Amount!");
        }
        if(depositBankAccount.getAccountStatus() != AccountStatus.ACTIVE){
            throw new BankSystemException("Bank Account with Number " + depositBankID + " Is not Active");
        }
        if(withdrawalBank.getAccountStatus() != AccountStatus.ACTIVE){
            throw new BankSystemException("Bank Account with Number " + withdrawalBankID + " Is not Active");
        }

        Transaction transaction = Transaction.builder().build();
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setToBankAccount(withdrawalBank);
        transaction.setFromBankAccount(depositBankAccount);
        transaction.setCurrency(Currency.ILS);
        transaction.setCustomerID(request.getCustomerID());
        transaction.setDescription("Transferred " + request.getAmount().toString() +
                " From Account Number " + request.getFromBankAccount() + " To Account Number "
                + request.getToBankAccount());

        TransactionDTO transactionDTO = TransactionDTO.builder().build();
        transactionDTO.set(transaction);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }


}
