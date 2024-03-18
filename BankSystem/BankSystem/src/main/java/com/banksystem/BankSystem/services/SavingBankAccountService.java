package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.exceptions.object_not_found.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.BankSystemException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.IndividualBankAccountRepository;
import com.banksystem.BankSystem.repository.SavingBankAccountRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class SavingBankAccountService extends BankAccountService<SavingBankAccount> {

    @Autowired
    private SavingBankAccountRepository savingBankAccountRepository;

    @Autowired
    private IndividualBankAccountRepository individualBankAccountRepository;

    @Override
    public ResponseEntity<Map<String, String>> closeAccount(final String accountNumber) throws BankAccountNotFoundException, CloseAccountException {
        SavingBankAccount account = this.findBankAccount(accountNumber);
        if(account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0){
            throw new CloseAccountException("Cannot Close Account, You need to withdraw your balance");
        }else if(account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0){
            throw new CloseAccountException("Cannot Close Account, You have debts!");
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    @Override
    public BankAccountRepository<SavingBankAccount> getRepository() {
        return this.savingBankAccountRepository;
    }

    @Override
    public ResponseEntity<BankAccountDTO> createBankAccount(final CreateBankAccountRequestDTO requestDTO) throws UserNotFoundException {
        SavingBankAccount savingBankAccount = SavingBankAccount.builder().build();
        savingBankAccount.setAssociateBankAccountNumber(requestDTO.getAssociatedBankAccountNumber());
        this.completeBankAccountDetails(savingBankAccount, requestDTO);
        SavingBankAccountDTO bankAccountDTO = new SavingBankAccountDTO();
        bankAccountDTO.set(savingBankAccount);
        return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) throws BankSystemException {
        throw new BankSystemException("Cannot Transfer Money From Saving Account!");
    }


    public ResponseEntity<Map<String, String>> withdrawAllSavings(final String accountNumber) throws BankSystemException {
        SavingBankAccount account = this.findBankAccount(accountNumber);
        if(account.getBalance().compareTo(BigDecimal.valueOf(0)) == 0){
            throw new BankSystemException("Saving Account with Number " + accountNumber + " has 0 Balance");
        }
        String associatedBankAccountID = account.getAssociateBankAccountNumber();
        Optional<BankAccount> associatedAccountSearchResult = this.getRepository().findByAccountNumberAllTables(associatedBankAccountID);
        if(associatedAccountSearchResult.isEmpty()){
            throw new BankSystemException("Couldn't find associated Bank account to this saving account!!");
        }
        BankAccount associatedAccount = associatedAccountSearchResult.get();
        associatedAccount.setBalance(associatedAccount.getBalance().add(account.getBalance()));

        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }


}
