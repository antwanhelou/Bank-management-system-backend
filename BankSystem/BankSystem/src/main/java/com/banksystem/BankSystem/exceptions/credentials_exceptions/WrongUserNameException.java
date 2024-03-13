package com.banksystem.BankSystem.exceptions.credentials_exceptions;

import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;

public class WrongUserNameException extends ResetCredentialsException {
    public WrongUserNameException(String msg) {
        super(msg);
    }
}
