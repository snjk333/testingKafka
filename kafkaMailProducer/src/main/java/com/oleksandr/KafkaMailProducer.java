package com.oleksandr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaMailProducer
{
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaMailProducer.class, args);
    }
}
