package com.banksystem.BankSystem.beans.components;


import com.banksystem.BankSystem.enums.Currency;
import com.banksystem.BankSystem.services.ExchangeRateResponse;
import com.banksystem.BankSystem.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyScheduler {


    @Autowired
    private RestTemplate restTemplate;
    private final Map<Currency, BigDecimal> exchangeRates = new HashMap<>();


    @Scheduled(cron = "0 0 0 * * ?") // Executes at midnight every day
    public void updateExchangeRates() {
        System.out.println("Fetching New Exchange Rates");

        String url = Constants.EXCHANGE_RATES_URL;
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            exchangeRates.clear(); // Clear old rates

            for(String curr: response.getBody().getConversionRates().keySet()){
                exchangeRates.put(Currency.fromString(curr), response.getBody().getConversionRates().get(curr));
            }
        }
    }

    public Map<Currency, BigDecimal> getExchangeRates(){
        return this.exchangeRates;
    }
}
