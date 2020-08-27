package com.redis.config;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLockKey {

	int order() default 0;

	String param() default "";

}
