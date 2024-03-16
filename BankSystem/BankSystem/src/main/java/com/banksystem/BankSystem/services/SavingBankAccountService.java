package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.SavingBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
@Service
public class SavingBankAccountService extends BankAccountService<SavingBankAccount> {

    @Autowired
    private SavingBankAccountRepository savingBankAccountRepository;

    @Override
    public ResponseEntity<Map<String, String>> closeAccount(UUID accountID) throws BankAccountNotFoundException, CloseAccountException {
        return null;
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

   /* @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) {
        return null;
    }*/


    @Override
    public ResponseEntity<TransactionDTO> depositMoney(DepositDTO request){
        throw new IllegalArgumentException();

    }
}
