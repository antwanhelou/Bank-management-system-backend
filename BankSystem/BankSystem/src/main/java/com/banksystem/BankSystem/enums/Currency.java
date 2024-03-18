package com.banksystem.BankSystem.enums;

public enum Currency {
    ILS, USD, EUR;

    public static Currency fromString(String currencyString) {
        return switch (currencyString) {
            case "ILS" -> ILS;
            case "USD" -> USD;
            case "EUR" -> EUR;
            default -> throw new IllegalArgumentException("Unknown currency: " + currencyString);
        };
    }
}