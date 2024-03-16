package com.banksystem.BankSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service

public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    // Store exchange rates in a local cache
    private Map<String, BigDecimal> exchangeRates = new HashMap<>();

    @Scheduled(cron = "0 0 0 * * ?") // Executes at midnight every day
    public void updateExchangeRates() {
        String url = "https://v6.exchangerate-api.com/v6/6291ab59f6ec48bc97cbefea/latest/USD";
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            exchangeRates.clear(); // Clear old rates
            exchangeRates.putAll(response.getBody().getConversionRates()); // Update with new rates
        } else {
            // Handle error or log if the API call failed
        }
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal fromRate = exchangeRates.getOrDefault(fromCurrency, BigDecimal.ONE);
        BigDecimal toRate = exchangeRates.getOrDefault(toCurrency, BigDecimal.ONE);
        return amount.multiply(toRate).divide(fromRate, 2, RoundingMode.HALF_UP);
    }

    // Additional methods and inner classes for handling API responses...
}
