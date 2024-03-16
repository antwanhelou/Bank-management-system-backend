package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.*;
import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.entities.loans.Loan;
import com.banksystem.BankSystem.enums.LoanStatus;
import com.banksystem.BankSystem.exceptions.BankAccountNotFoundException;
import com.banksystem.BankSystem.exceptions.CloseAccountException;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.IndividualBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
public class IndividualBankAccountService extends BankAccountService<IndividualBankAccount>{

    @Autowired
    private IndividualBankAccountRepository individualBankAccountRepository;

    @Override
    public ResponseEntity<Map<String, String>> closeAccount(final UUID accountID) throws BankAccountNotFoundException, CloseAccountException {
        IndividualBankAccount account = this.findBankAccount(accountID);
        if(account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0){
            throw new CloseAccountException("Cannot Close Account, You need to withdraw your balance");
        }else if(account.getBalance().compareTo(BigDecimal.valueOf(0)) < 0){
            throw new CloseAccountException("Cannot Close Account, You have debts!");
        }
        if(checkForActiveLoans(account)){
            throw new CloseAccountException("Cannot Close Account, You still have unpaid loans");
        }
        return null;


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
        IndividualBankAccountDTO bankAccountDTO = new IndividualBankAccountDTO();
        bankAccountDTO.set(individualBankAccount);
        return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
    }

   /* @Override
    public ResponseEntity<TransactionDTO> transferMoney(TransferRequestDTO request) {
        return null;
    }*/


}
