package com.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @HystrixCommand(
            // 一组 Hystrix 命令的集合， 用来统计、报告，默认取类名，可不配置。
            groupKey = "groupkey",
            // 用来标识一个 Hystrix 命令，默认会取被注解的方法名。需要注意：Hystrix 里同一个键的唯一标识并不包括 groupKey，建议取一个独一二无的名字，防止多个方法之间因为键重复而互相影响。
            commandKey = "commandKey",
            // 用来标识一个线程池，如果没设置的话会取 groupKey，很多情况下都是同一个类内的方法在共用同一个线程池，如果两个共用同一线程池的方法上配置了同样的属性，在第一个方法被执行后线程池的属性就固定了，所以属性会以第一个被执行的方法上的配置为准。
            threadPoolKey = "threadPoolKey",
            // 方法执行时熔断、错误、超时时会执行的回退方法，需要保持此方法与 Hystrix 方法的签名和返回值一致。
            fallbackMethod = "onError",
            // 与此命令相关的属性
            commandProperties = {
                    // 该属性用来设置HystrixCommand.run()执行的隔离策略。默认为THREAD。
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    // 该属性用来配置HystrixCommand执行的超时时间，单位为毫秒。
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    // 该属性用来配置HystrixCommand.run()的执行是否启用超时时间。默认为true。
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    // 该属性用来配置当HystrixCommand.run()执行超时的时候是否要它中断。
//                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = ""),
                    // 该属性用来配置当HystrixCommand.run()执行取消时是否要它中断。
//                    @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = ""),
                    // 当HystrixCommand命令的隔离策略使用信号量时，该属性用来配置信号量的大小。当最大并发请求达到该设置值时，后续的请求将被拒绝。
//                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = ""),
                    // 确定当服务请求命令失败时，是否使用断路器来跟踪其健康指标和熔断请求。默认为true。
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    // 用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候，如果滚动时间窗（默认10秒）内仅收到19个请求，即使这19个请求都失败了，断路器也不会打开。
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    // 用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后，会将断路器设置为“半开”状态，尝试熔断的请求命令，如果依然时候就将断路器继续设置为“打开”状态，如果成功，就设置为“关闭”状态。
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = ""),
                    // 该属性用来设置断路器打开的错误百分比条件。默认值为50，表示在滚动时间窗中，在请求值超过requestVolumeThreshold阈值的前提下，如果错误请求数百分比超过50，就把断路器设置为“打开”状态，否则就设置为“关闭”状态。
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    // 该属性默认为false。如果该属性设置为true，断路器将强制进入“打开”状态，它会拒绝所有请求。该属性优于forceClosed属性。
                    @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
                    // 该属性默认为false。如果该属性设置为true，断路器强制进入“关闭”状态，它会接收所有请求。如果forceOpen属性为true，该属性不生效。
                    @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false")
            },
            // 与线程池相关的属性
            threadPoolProperties = {
                    // 核心线程池的大小，默认值是 10，一般根据 QPS * 99% cost + redundancy count 计算得出。
                    @HystrixProperty(name = "coreSize", value = "5"),
                    // 是否允许线程池扩展到最大线程池数量，默认为 false;
//                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
                    // 线程池中线程的最大数量，默认值是 10，此配置项单独配置时并不会生效，需要启用 allowMaximumSizeToDivergeFromCoreSize 项。
//                    @HystrixProperty(name = "maximumSize", value = "5"),
                    // 作业队列的最大值，默认值为 -1，设置为此值时，队列会使用 SynchronousQueue，此时其 size 为0，Hystrix 不会向队列内存放作业。如果此值设置为一个正的 int 型，队列会使用一个固定 size 的 LinkedBlockingQueue，此时在核心线程池内的线程都在忙碌时，会将作业暂时存放在此队列内，但超出此队列的请求依然会被拒绝。
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    // 由上面的 maximumSize，我们知道，线程池内核心线程数目都在忙碌，再有新的请求到达时，线程池容量可以被扩充为到最大数量，等到线程池空闲后，多于核心数量的线程还会被回收，此值指定了线程被回收前的存活时间，默认为 2，即两分钟。
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2")
            }
    )
    @GetMapping(value = "/test")
    public String test(@RequestParam("userName") String userName) {
        log.info("receive_name:{}", userName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        log.info("------------------------------");
        return userName;
    }

    @HystrixCommand(
            fallbackMethod = "onError"
    )
    @GetMapping("/test2")
    public String test2(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new RuntimeException("name不能为空");
        }
        return "Good morning, " + userName;
    }

    /**
     * 回调函数参数需要和接口参数一致
     */
    public String onError(String userName, Throwable e) {
        return "Error!!!" + userName;
    }

}