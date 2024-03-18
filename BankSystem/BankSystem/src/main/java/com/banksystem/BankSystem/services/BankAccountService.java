package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.TransactionType;
import com.banksystem.BankSystem.exceptions.*;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.TransactionRepository;
import com.banksystem.BankSystem.utilities.Constants;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import com.banksystem.BankSystem.repository.BankAccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service

public abstract class BankAccountService<T extends BankAccount> {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    protected CustomerService customerService;
    @Autowired
    private CurrencyService currencyService;


    public T findBankAccount(final String accountNumber) throws BankAccountNotFoundException {
        Optional<T> searchResult = this.getRepository().findByAccountNumber(accountNumber);
        if(searchResult.isEmpty()){
            throw new BankAccountNotFoundException("Cannot find Bank Account with number : " + accountNumber);
        }
        return searchResult.get();
    }
    public ResponseEntity<Map<String, String>> suspendAccount(final String accountNumber) throws BankAccountNotFoundException {
        T account = this.findBankAccount(accountNumber);
        account.setAccountStatus(AccountStatus.SUSPENDED);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> activateAccount(final String accountNumber) throws BankAccountNotFoundException {
        T account = this.findBankAccount(accountNumber);
        account.setAccountStatus(AccountStatus.ACTIVE);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public abstract ResponseEntity<Map<String, String>> closeAccount(final String accountNumber) throws BankAccountNotFoundException, CloseAccountException;

    public abstract BankAccountRepository<T> getRepository();
    public abstract ResponseEntity<BankAccountDTO> createBankAccount(final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException;

    protected void completeBankAccountDetails(T bankAccount, CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        bankAccount.init();
        Customer owner = customerService.getUser(requestDTO.getOwnerID());
        bankAccount.setOwner(owner);
        bankAccount.setMinimumBalance(requestDTO.getMinimumBalance());
        bankAccount.setAccountNumber(this.generateUniqueRandomBankAccountNumber());
        bankAccount.setAccountStatus(AccountStatus.ACTIVE);
        bankAccount.setBalance(new BigDecimal(Constants.INITIAL_BANK_ACCOUNT_BALANCE));
        bankAccount.setBankCode(Constants.BANK_CODE);
        bankAccount.setBranchCode(Constants.BRANCH_NUMBER);
        this.getRepository().save(bankAccount);
        customerService.saveCustomer(owner);
    }



    private String generateUniqueRandomBankAccountNumber(){
        String characters = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        Optional<BankAccount> bankAccounts;
        for (int i = 0; i < 13; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        bankAccounts = this.getRepository().findByAccountNumberAllTables(sb.toString());
        while(bankAccounts.isPresent()){
            sb = new StringBuilder();
            for (int i = 0; i < 13; i++) {
                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                sb.append(randomChar);
            }
            bankAccounts = this.getRepository().findByAccountNumberAllTables(sb.toString());
        }

        return sb.toString();

    }

    public ResponseEntity<TransactionDTO> depositMoney(DepositOrWithdrawDTO depositDTO) throws BankSystemException {
        T account = this.findBankAccount(depositDTO.getBankAccountNumber());
        BigDecimal amountInNIS;
        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new BankSystemException("Bank Account with Number " + depositDTO.getBankAccountNumber() + " Is not Active");
        }

        if (depositDTO.getCurrency().equals(Currency.ILS)) {
            amountInNIS = depositDTO.getAmount();
        } else {
            amountInNIS = currencyService.convertCurrency(depositDTO.getAmount(), depositDTO.getCurrency(), Currency.ILS);
        }
        account.setBalance(account.getBalance().add(amountInNIS));

        Transaction transaction = Transaction.builder().build();
        transaction.setTransactionDate(Instant.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setCurrency(depositDTO.getCurrency());
        transaction.setToBankAccount(account);
        transaction.setAmount(amountInNIS);
        transaction.setDescription(amountInNIS + depositDTO.getCurrency().toString() + Constants.DEPOSIT_MESSAGE);
        transaction.setCustomerID(depositDTO.getCustomerID());
        TransactionDTO transactionDTO = TransactionDTO.builder().build();
        transactionDTO.set(transaction);
        transactionRepository.save(transaction);
        this.getRepository().save(account);

        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> viewBalance(final String accountNumber) throws BankAccountNotFoundException {
        T account = this.findBankAccount(accountNumber);
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    protected abstract ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) throws BankSystemException;

    public ResponseEntity<TransactionDTO> withdrawMoney(DepositOrWithdrawDTO withdrawDTO) throws BankSystemException {
        T account = this.findBankAccount(withdrawDTO.getBankAccountNumber());
        BigDecimal withdrawalAmount = withdrawDTO.getAmount();
        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new BankSystemException("Bank Account with Number " + withdrawDTO.getBankAccountNumber() + " Is not Active");
        }

        if (!withdrawDTO.getCurrency().equals(Currency.ILS)) {
            withdrawalAmount = currencyService.convertCurrency(withdrawalAmount, withdrawDTO.getCurrency(), Currency.ILS);
        }

        if (account.getBalance().compareTo(BigDecimal.valueOf(withdrawalAmount.doubleValue())) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        account.setBalance(account.getBalance().subtract(withdrawalAmount));
        this.getRepository().save(account);

        Transaction transaction = new Transaction();
        transaction.setToBankAccount(account);
        transaction.setAmount(withdrawalAmount.negate());
        transaction.setTransactionDate(Instant.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setCustomerID(withdrawDTO.getCustomerID());
        transaction.setCurrency(withdrawDTO.getCurrency());
        transaction.setCurrency(Currency.ILS);

        TransactionDTO transactionDTO = TransactionDTO.builder().build();
        transactionDTO.set(transaction);
        transactionRepository.save(transaction);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }


}
