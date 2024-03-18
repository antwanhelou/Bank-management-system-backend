package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.beans.components.CurrencyScheduler;
import com.banksystem.BankSystem.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service

public class CurrencyService {

    @Autowired
    private CurrencyScheduler currencyScheduler;


    public BigDecimal convertCurrency(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        Map<Currency, BigDecimal> exchangeRates = currencyScheduler.getExchangeRates();
        BigDecimal fromRate = exchangeRates.getOrDefault(fromCurrency, BigDecimal.ONE);
        BigDecimal toRate = exchangeRates.getOrDefault(toCurrency, BigDecimal.ONE);
        return amount.multiply(toRate).divide(fromRate, 2, RoundingMode.HALF_UP);
    }
}
