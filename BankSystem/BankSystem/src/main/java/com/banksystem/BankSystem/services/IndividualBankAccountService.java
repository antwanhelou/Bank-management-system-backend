package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.IndividualBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualBankAccountService extends BankAccountService<IndividualBankAccount>{


    @Autowired
    private final IndividualBankAccountRepository individualBankAccountRepository;

    public IndividualBankAccountService(IndividualBankAccountRepository individualBankAccountRepository) {
        this.individualBankAccountRepository = individualBankAccountRepository;
    }

    @Override
    protected BankAccountRepository<IndividualBankAccount> getRepository() {
        return this.individualBankAccountRepository;
    }
}
