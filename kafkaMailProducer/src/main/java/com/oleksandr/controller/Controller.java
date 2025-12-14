package com.oleksandr.controller;

import com.oleksandr.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final KafkaProducer kafkaMailProducer;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestParam String message){
        kafkaMailProducer.sendMessage(message);
    }

}
