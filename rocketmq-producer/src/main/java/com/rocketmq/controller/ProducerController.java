package com.rocketmq.controller;

import com.rocketmq.config.RocketMqProperties;
import com.rocketmq.mq.producer.SendContext;
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
    private RocketMqProperties rocketMqProperties;

    @GetMapping(path = "/send")
    public String report(String msg) {
        for (int i = 0; i < 2; i++) {
            String key = UUID.randomUUID().toString();
            SendContext.sendMsg(rocketMqProperties.getTopic(), rocketMqProperties.getTag(), key, msg + i);
            SendContext.sendMsg(key, msg + i);
        }
        return "success";
    }

}
