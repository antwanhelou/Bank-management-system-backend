package com.banksystem.BankSystem.exceptions.object_not_found;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class RequestNotFoundException extends EntityNotFoundException {
    public RequestNotFoundException(String msg) {
        super(msg);
    }
}
