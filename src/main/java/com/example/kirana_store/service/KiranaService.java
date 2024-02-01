package com.example.kirana_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.kirana_store.model.KiranaStore;
import com.example.kirana_store.repository.KiranaStoreRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class KiranaService {

    @Autowired
    private KiranaStoreRepository kiranaStoreRepository;

    @Autowired
    private CurrencyConversionService currencyConversionService; // Assuming you have a service for currency conversion

    @Transactional
    public KiranaStore saveTransaction(KiranaStore transaction) {
        // Implement currency conversion using the provided API
        double convertedAmount = currencyConversionService.convertToINR(transaction.getAmount(), transaction.getCurrency());
        transaction.setAmount(convertedAmount);

        return kiranaStoreRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<KiranaStore> getDailyTransactionsReport(LocalDateTime date) {
        LocalDateTime start = date.with(LocalTime.MIN);
        LocalDateTime end = date.with(LocalTime.MAX);

        return kiranaStoreRepository.findByTimestampBetween(start, end);
    }

    // Additional method for handling race conditions
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public KiranaStore processTransactionWithLock(Long id) {
        // Fetch the transaction by ID
        KiranaStore transaction = kiranaStoreRepository.findById(id).orElse(null);

        if (transaction != null) {
            // Perform the necessary operations on the transaction
            // ...

            // Save the updated transaction
            return kiranaStoreRepository.save(transaction);
        }

        return null;
    }
}
