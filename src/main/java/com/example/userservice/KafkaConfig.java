package com.example.userservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {
    KafkaTemplate<String, String> kafkaTemplate;
    public KafkaConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String topic,String message) {
        kafkaTemplate.send(topic,message);
    }
}
