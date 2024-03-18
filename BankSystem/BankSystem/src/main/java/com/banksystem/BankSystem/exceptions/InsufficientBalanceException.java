package com.banksystem.BankSystem.exceptions;

public class InsufficientBalanceException extends BankSystemException{
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
