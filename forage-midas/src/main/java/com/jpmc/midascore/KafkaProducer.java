package com.jpmc.midascore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${general.kafka-topic}")
    private String topic;

    public KafkaProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void send(String transactionJson) {
        try {
            Transaction transaction = objectMapper.readValue(transactionJson, Transaction.class);
            kafkaTemplate.send(topic, transaction);
        } catch (Exception e) {
            throw new RuntimeException("Error processing transaction", e);
        }
    }
}