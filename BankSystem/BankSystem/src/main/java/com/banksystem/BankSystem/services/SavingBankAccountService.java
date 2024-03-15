package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.SavingBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SavingBankAccountService extends BankAccountService<SavingBankAccount> {

    @Autowired
    private final SavingBankAccountRepository savingBankAccountRepository;

    public SavingBankAccountService(SavingBankAccountRepository savingBankAccountRepository) {
        this.savingBankAccountRepository = savingBankAccountRepository;
    }

    @Override
    protected BankAccountRepository<SavingBankAccount> getRepository() {
        return this.savingBankAccountRepository;
    }
}
