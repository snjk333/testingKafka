package com.oleksandr.kafka;

import com.oleksandr.common.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void sendMessage(NotificationRequest message, int i){
        kafkaTemplate.send("mailNotifications", String.valueOf(i) ,message);
    }

}
