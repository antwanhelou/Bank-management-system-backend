package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
import com.banksystem.BankSystem.repository.BankAccountRepository;
import com.banksystem.BankSystem.repository.JointBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JointBankAccountService extends BankAccountService<JointBankAccount> {

    @Autowired
    private final JointBankAccountRepository jointBankAccountRepository;

    public JointBankAccountService(JointBankAccountRepository jointBankAccountRepository) {
        this.jointBankAccountRepository = jointBankAccountRepository;
    }

    @Override
    protected BankAccountRepository<JointBankAccount> getRepository() {
        return this.jointBankAccountRepository;
    }
}
