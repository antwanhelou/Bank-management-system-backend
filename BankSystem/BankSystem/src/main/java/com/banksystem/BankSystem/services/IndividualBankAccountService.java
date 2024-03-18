package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.entities.transactions.Transaction;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.enums.LoanStatus;
import com.banksystem.BankSystem.enums.TransactionType;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.InsufficientBalanceException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.*;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Primary
public class IndividualBankAccountService extends BankAccountService<IndividualBankAccount>{

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private IndividualBankAccountRepository individualBankAccountRepository;

    @Autowired
    private JointBankAccountRepository jointBankAccountRepository;

    @Override
    public ResponseEntity<Map<String, String>> closeAccount(final String accountNumber) throws BankAccountNotFoundException, CloseAccountException {
        IndividualBankAccount account = this.findBankAccount(accountNumber);
        if(account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0){
            throw new CloseAccountException("Cannot Close Account, You need to withdraw your balance");
        }else if(account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0){
            throw new CloseAccountException("Cannot Close Account, You have debts!");
        }
        if(checkForActiveLoans(account)){
            throw new CloseAccountException("Cannot Close Account, You still have unpaid loans");
        }
        if(!account.getAssociatedBankAccounts().isEmpty()){
            throw new CloseAccountException("Cannot Close Account, You still have open save accounts, Please close them first!");
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);

    }

    private boolean checkForActiveLoans(IndividualBankAccount bankAccount){
        for(Loan loan: bankAccount.getLoans()){
            if (loan.getStatus() == LoanStatus.ACTIVE){
                return true;
            }
        }
        return false;
    }

    @Override
    public BankAccountRepository<IndividualBankAccount> getRepository() {
        return this.individualBankAccountRepository;
    }

    @Override
    public ResponseEntity<BankAccountDTO> createBankAccount(final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        IndividualBankAccount individualBankAccount = IndividualBankAccount.builder().build();
        this.completeBankAccountDetails(individualBankAccount, requestDTO);
        individualBankAccount.setCreditCards(new HashSet<>());
        IndividualBankAccountDTO bankAccountDTO = IndividualBankAccountDTO.builder().build();
        bankAccountDTO.set(individualBankAccount);
        return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) throws BankAccountNotFoundException, InsufficientBalanceException {
        final String depositBankID = request.getToBankAccount();
        final String withdrawalBankID = request.getFromBankAccount();
        final BigDecimal amount = request.getAmount();
        IndividualBankAccount withdrawalBank = this.findBankAccount(withdrawalBankID);

        Optional<BankAccount> depositBankSearchResult = this.getRepository().findByAccountNumberAllTables(depositBankID);
        if(depositBankSearchResult.isEmpty()){
            throw new BankAccountNotFoundException("Bank Account " + depositBankID + " not found!");
        }
        BankAccount depositBankAccount = depositBankSearchResult.get();
        if(withdrawalBank.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("You Do Not Have The Desired Transfer Amount!");
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


    public ResponseEntity<Map<String, String>> provideLoanToCustomer(LoanDTO loanDTO) throws BankAccountNotFoundException {
        IndividualBankAccount account = this.findBankAccount(loanDTO.getAccountNumber());
        Loan loan = new Loan();
        loan.setAmount(loan.getAmount());
        loan.setInterestRate(loan.getInterestRate());
        loan.setDueDate(loan.getDueDate());
        loan.setBankAccount(account);
        account.addLoan(loan);
        this.getRepository().save(account);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }


}
