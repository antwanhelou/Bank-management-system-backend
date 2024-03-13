package com.banksystem.BankSystem.exceptions;


import lombok.ToString;

public class BankSystemException extends Exception{
    String msg;

    public BankSystemException(String msg){
        this.msg = msg;
    }
    @Override
    public String getMessage(){
        return this.msg;
    }

}
