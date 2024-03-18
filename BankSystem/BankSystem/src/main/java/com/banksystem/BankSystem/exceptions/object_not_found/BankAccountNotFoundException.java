package com.banksystem.BankSystem.exceptions.object_not_found;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class BankAccountNotFoundException extends EntityNotFoundException {
    public BankAccountNotFoundException(String msg) {
        super(msg);
    }
}
