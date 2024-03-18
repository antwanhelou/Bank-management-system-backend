package com.banksystem.BankSystem.exceptions.object_not_found;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
