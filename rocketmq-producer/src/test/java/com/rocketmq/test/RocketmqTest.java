package com.rocketmq.test;

import com.rocketmq.ProducerApplication;
import com.rocketmq.mq.producer.SendContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = ProducerApplication.class)
public class RocketmqTest {

    /**
     * 测试查询 - 注解方式
     */
    @Test
    public void testGetAnnotation() {
        for (int i = 0; i < 2; i++) {
            String key = UUID.randomUUID().toString();
            SendContext.sendMsg(key, "msg" + i);
        }
    }

}
