package com.kafka.controller;

import com.kafka.producer.SendMessageKafka;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/producer")
public class ProducerController {
    @Autowired
    private SendMessageKafka sendMessageKafka;

    @GetMapping(path = "/send")
    public String report(String msg) {
        for (int i = 0; i < 2; i++) {
            String key = UUID.randomUUID().toString();
            sendMessageKafka.sendMsg(msg + i, key);
        }
        return "success";
    }

}
