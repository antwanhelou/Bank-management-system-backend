package com.banksystem.BankSystem.exceptions.object_not_found;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class EntityNotFoundException extends BankSystemException {


    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
