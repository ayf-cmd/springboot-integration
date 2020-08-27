package com.redis.controller;

import com.redis.config.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/redis")
public class TestRedisController {
	@Autowired
	private RedisClient redisClient;

	@GetMapping(path = "/test")
	public String redis() {
		log.info("redisTest - start ");
		// 1> 参数验证
		try {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String aa = df.format(date);
			String key = "key:" + aa;
			redisClient.set("key:" + aa, aa, 100, TimeUnit.SECONDS);
			log.info("set success ");
			String test = redisClient.get(key);
			log.info("get -> {} . ", test);
		}catch (NullPointerException | IllegalArgumentException e) {
			log.error("error : ", e);
			return e.getMessage();
		}catch (Exception e) {
			log.error("error : ", e);
			return e.getMessage();
		}
		log.info("redisTest - end ");

		log.info("lock - check - start . ");
		try {
			RLock lock = redisClient.getLock("test-lock");
			try {
				lock.lock();
				log.info("redisLock执行中");
				new Thread(new Runnable() {
					@Override
					public void run() {
						RLock lock = redisClient.getLock("test-lock");
						try {
							lock.lock();
							log.info("redisLock2执行中.......");
						} finally {
							if (lock != null) {
								lock.unlock();
							}
							log.info("redisLock2执行结束");
						}
					}
				}).start();
				Thread.sleep(1000 * 5);
			} finally {
				if (lock != null) {
					lock.unlock();
				}
				log.info("redisLock执行结束");
			}
			Thread.sleep(1000 * 3);
			log.info("lock - check - end . ");
			return "lock";
		}catch (NullPointerException | IllegalArgumentException e) {
			log.error("error : ", e);
			return e.getMessage();
		}catch (Exception e) {
			log.error("error : ", e);
			return e.getMessage();
		}
	}

}
