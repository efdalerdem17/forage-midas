package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public BalanceService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Balance getBalanceForUser(Long userId) {
        float balance = transactionRepository.findBalanceByUserId(userId);
        return new Balance(balance);
    }
}