package com.banksystem.BankSystem.exceptions.object_not_found;

import com.banksystem.BankSystem.exceptions.BankSystemException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
