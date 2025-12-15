package com.oleksandr.kafkaIntegration;

import com.oleksandr.common.notification.NotificationRequest;
import com.oleksandr.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailServiceImpl mailService;

    @KafkaListener(topics = "mailNotifications", groupId = "mail_consumer")
    public void listen(NotificationRequest message){
        System.out.println("I get message.");
        mailService.sendEmail(message);
    }


}
