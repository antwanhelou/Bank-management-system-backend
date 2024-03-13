package com.banksystem.BankSystem.exceptions;

public class UserNotFoundException extends BankSystemException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
