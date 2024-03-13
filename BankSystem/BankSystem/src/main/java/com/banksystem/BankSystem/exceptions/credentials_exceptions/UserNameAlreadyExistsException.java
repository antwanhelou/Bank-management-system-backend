package com.banksystem.BankSystem.exceptions.credentials_exceptions;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class UserNameAlreadyExistsException extends BankSystemException {
    public UserNameAlreadyExistsException(String msg) {
        super(msg);
    }
}
