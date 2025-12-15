package com.oleksandr.controller;

import com.oleksandr.common.notification.NotificationRequest;
import com.oleksandr.common.notification.UserForMailDTO;
import com.oleksandr.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.oleksandr.common.enums.MAIL_TYPE.REGISTRATION_CONFIRM;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final KafkaProducer kafkaMailProducer;

    @PostMapping("/sendMessage")
    public void sendMessage(NotificationRequest message){
        kafkaMailProducer.sendMessage(message, 1);
    }

    @PostMapping("/testMessage")
    public void sendMessage(){
        NotificationRequest message = new NotificationRequest(
                new UserForMailDTO("Oleksandr", "kulbitsanya2@gmail.com"),
                REGISTRATION_CONFIRM,
                Collections.emptyMap()
        );
        for(int i = 0; i < 10; i++){
            kafkaMailProducer.sendMessage(message, i);
        }
    }

}
