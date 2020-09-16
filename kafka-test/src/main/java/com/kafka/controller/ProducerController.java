package com.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping("message/send")
    public String send(String msg) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    kafkaTemplate.send("demo", msg); //使用kafka模板发送信息
                }
            }).start();
        }
        return "success";
    }

}