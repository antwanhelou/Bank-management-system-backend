package com.banksystem.BankSystem.exceptions.credentials_exceptions;

public class WrongUserNameAndPasswordException extends ResetCredentialsException{
    public WrongUserNameAndPasswordException(String msg) {
        super(msg);
    }
}
