package com.jasypt.test;

import com.jasypt.JasyptApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试类
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = JasyptApplication.class)
public class JasyptTest {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testEncryption() throws InterruptedException {
        System.err.println(this.entry("123"));
    }

    @Test
    public void testDecryption() throws InterruptedException {
        System.err.println(this.detry("ENC(LRnsPFsUTkrp4msVh/MwwrPQrRK1lroU)"));
    }

    private String entry(String msg) {
        return "ENC(" + stringEncryptor.encrypt(msg) + ")";
    }

    private String detry(String msg) {
        return stringEncryptor.decrypt(msg.substring(4, msg.length() - 1));
    }

}