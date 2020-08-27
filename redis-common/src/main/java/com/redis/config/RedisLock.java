package com.redis.config;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {

	String lockKey() default "";

	String prefixLockKey() default "";

	String suffixLockKey() default "";

	String separator() default "";

}
