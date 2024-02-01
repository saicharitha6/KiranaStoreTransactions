package com.example.kirana_store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.kirana_store.model.CurrencyConversionResponse;

@Service
public class CurrencyConversionService {

    private final Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);

    private final String API_URL = "https://api.fxratesapi.com/latest?base=%s";

    @Autowired
    private RestTemplate restTemplate;

    public double convertToINR(double amount, String sourceCurrency) {
        String requestUrl = String.format(API_URL, sourceCurrency);

        try {
            logger.info("Sending currency conversion request to: {}", requestUrl);

            CurrencyConversionResponse response = restTemplate.getForObject(requestUrl,
                    CurrencyConversionResponse.class);

            if (response != null && response.getRates() != null && response.getRates().containsKey("INR")) {
                double conversionRate = response.getRates().get("INR");
                logger.info("Currency conversion successful. Converted {} {} to INR at rate {}.", amount,
                        sourceCurrency, conversionRate);
                return amount * conversionRate;
            } else {
                throw new RestClientException("Error in currency conversion response");
            }
        } catch (RestClientException e) {
            logger.error("Error in currency conversion", e);
            throw new RestClientException("Error in currency conversion", e);
        }
    }
}
