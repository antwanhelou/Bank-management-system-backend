//package com.banksystem.BankSystem.beans;
//
//import com.banksystem.BankSystem.entities.bankaccounts.BankAccount;
//import com.banksystem.BankSystem.entities.bankaccounts.IndividualBankAccount;
//import com.banksystem.BankSystem.entities.bankaccounts.JointBankAccount;
//import com.banksystem.BankSystem.entities.bankaccounts.SavingBankAccount;
//import com.banksystem.BankSystem.repository.BankAccountRepository;
//import com.banksystem.BankSystem.services.BankAccountService;
//import com.banksystem.BankSystem.services.IndividualBankAccountService;
//import com.banksystem.BankSystem.services.JointBankAccountService;
//import com.banksystem.BankSystem.services.SavingBankAccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class BankAccountServiceFactory {
//    @Autowired
//    private IndividualBankAccountService individualBankAccountService;
//
//    @Autowired
//    private SavingBankAccountService savingBankAccountService;
//
//    @Autowired
//    private JointBankAccountService jointBankAccountService;
//
//    @Autowired
//    private BankAccountRepository bankAccountRepository;
//
//    public BankAccountService<? extends BankAccount> getServiceForAccount(UUID accountID) {
//        BankAccount account = bankAccountRepository.findById(accountID)
//                .orElseThrow(() -> new IllegalArgumentException("Bank account not found with ID: " + accountID));
//        if (account instanceof IndividualBankAccount) {
//            return individualBankAccountService;
//        } else if (account instanceof SavingBankAccount) {
//            return savingBankAccountService;
//        }else if (account instanceof JointBankAccount){
//            return jointBankAccountService;
//        }
//        throw new IllegalArgumentException("Unsupported account type");
//    }
//}
