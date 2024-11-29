package com.jpmc.midascore.service;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionKafkaListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionKafkaListener.class);

    private final DatabaseConduit databaseConduit;

    @Autowired
    public TransactionKafkaListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {
        logger.info("Received transaction with amount: {}", transaction.getAmount());
        databaseConduit.processTransaction(transaction);
    }
}