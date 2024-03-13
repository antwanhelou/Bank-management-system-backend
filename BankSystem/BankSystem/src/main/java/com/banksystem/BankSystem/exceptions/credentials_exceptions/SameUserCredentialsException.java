package com.banksystem.BankSystem.exceptions.credentials_exceptions;

import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;

public class SameUserCredentialsException extends ResetCredentialsException {

    public SameUserCredentialsException(String msg) {
        super(msg);
    }
}
