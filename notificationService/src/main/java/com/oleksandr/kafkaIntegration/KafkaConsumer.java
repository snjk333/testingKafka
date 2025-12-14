package com.oleksandr.kafkaIntegration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "mailNotifications", groupId = "mail_consumer")
    public void listen(String message){

        System.out.println("Received message: " + message);

    }


}
