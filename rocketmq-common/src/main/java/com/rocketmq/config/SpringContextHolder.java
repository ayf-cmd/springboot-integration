package com.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextAware 通过它Spring容器会自动把上下文环境对象调用ApplicationContextAware接口中的setApplicationContext方法。
 * 我们在ApplicationContextAware的实现类中，就可以通过这个上下文环境对象得到Spring容器中的Bean。
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 */
@Slf4j
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    // 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        String activeProfile = applicationContext.getEnvironment().getActiveProfiles()[0];
        log.info("properties - 应用程序上下文 ： [{}] 初始化完成", activeProfile);
        context = applicationContext;
    }

    // 取得存储在静态变量中的ApplicationContext.
    public static ApplicationContext getApplicationContext() {
        //assertContextInjected();
        return context;
    }

    // 清除applicationContext静态变量.
    public static void clearHolder() {
        context = null;
    }

    // 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
    public static <T> T getBean(Class<T> requiredType) {
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }

    //  从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }

    // 判断application是否为空
    public static void assertContextInjected() {
        Validate.isTrue(context == null, "application未注入 ，请在springContext.xml中注入SpringHolder!");
    }

}
