package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
import com.banksystem.BankSystem.enums.AccountStatus;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.SavingBankAccountRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
@Service
public class SavingBankAccountService extends BankAccountService<SavingBankAccount> {

    @Autowired
    private SavingBankAccountRepository savingBankAccountRepository;

    @Override
    public ResponseEntity<Map<String, String>> closeAccount(UUID accountID) throws BankAccountNotFoundException, CloseAccountException {
        SavingBankAccount account = this.findBankAccount(accountID);
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
        this.completeBankAccountDetails(savingBankAccount, requestDTO);
        SavingBankAccountDTO bankAccountDTO = new SavingBankAccountDTO();
        bankAccountDTO.set(savingBankAccount);
        return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) {
        return null;
    }


    @Override
    public ResponseEntity<TransactionDTO> deposit(DepositDTO request){
        throw new IllegalArgumentException();

    }
}
