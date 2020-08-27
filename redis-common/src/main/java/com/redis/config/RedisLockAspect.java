package com.redis.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Aspect
@Slf4j
@Data
@AllArgsConstructor
public class RedisLockAspect {
    @Autowired
    private RedisClient redisClient;
    
    @Around("@annotation(RedisLock)")
    public Object lock(ProceedingJoinPoint point) throws Throwable {
        log.debug("1");
        Object object = null;
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        Object[] args = point.getArgs();
        String lockName = getLockName(redisLock, method, args);
        RLock lock = redisClient.getLock(lockName);
        lock.lock();
        if (!Thread.currentThread().isInterrupted()) {
            log.debug(Thread.currentThread().getName() + " gets lock.");
            try {
                object = point.proceed();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
                log.debug(Thread.currentThread().getName() + " release lock.");
            }
        } else {
            log.debug(Thread.currentThread().getName() + " does not get lock.");
        }
        // lock.tryLock(redisLock.maxWaitTimeInMills(), redisLock.keyExpireTimeInMills(), TimeUnit.MILLISECONDS); todo trylock
        return object;
    }
    
    private String getLockName(RedisLock redisLock, Method method, Object[] args) {
        String lockName = redisLock.lockKey();
        if("".equals(lockName)) {
            String prefixLockKey = redisLock.prefixLockKey();
            String suffixLockKey = redisLock.suffixLockKey();
            String separator = redisLock.separator();
            List<String> nameList = new ArrayList<>();
            if (!"".equals(prefixLockKey)) {
                nameList.add(prefixLockKey);
            }
            SortedMap<Integer, String> params = sortLockParam(method, args);
            nameList.addAll(params.values());
            if(!"".equals(suffixLockKey)) {
                nameList.add(suffixLockKey);
            }
            lockName = String.join(separator, nameList);
        }
        log.debug("redis lock key: {}", lockName);
        return lockName;
    }

    private SortedMap<Integer, String> sortLockParam(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        SortedMap<Integer, String> keys = new TreeMap<>();
        int length = parameterAnnotations.length;
        for (int i = 0; i < length; i ++) {
            RedisLockKey redisLockKey = getAnnotationByClassType(RedisLockKey.class, parameterAnnotations[i]);
            if (redisLockKey != null) {
                Object arg = args[i];
                if (arg != null) {
                    String param = redisLockKey.param();
                    if(!"".equals(param)) {
                        keys.put(redisLockKey.order(), getParamByName(arg, param));
                    } else {
                        keys.put(redisLockKey.order(), arg.toString());
                    }
                }
            }
        }
        return keys;
    }

	private <T extends Annotation> T getAnnotationByClassType(Class<T> classType, Annotation[] annotations) {
        if(annotations != null && annotations.length > 0) {
            for(Annotation annotation : annotations) {
                if(classType.equals(annotation.annotationType())) {
                    return (T)annotation;
                }
            }
        }
        return null;
    }
    
    private String getParamByName(Object arg, String name) {
        if (!"".equals(name) && arg != null) {
            try {
                return String.valueOf(PropertyUtils.getProperty(arg, name));
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "has no property named " + name, e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    
}
