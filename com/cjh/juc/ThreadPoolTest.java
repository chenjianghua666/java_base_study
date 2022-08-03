package com.cjh.juc;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadPoolTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 17:46
 * @Version 1.0
 */
public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 5; // 核心线程数
    private static final int MAX_POOL_SIZE = 10; // 线程池最大线程数
    private static final int QUEUE_CAPACITY = 10; // 任务队列,用来存储等待执行任务的队列
    private static final Long KEEP_ALIVE_TIME = 1L;
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadFactory() { // 自定义线程名称
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "my thread" + r.hashCode());
                    }
                },
        new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("current thread name: "+Thread.currentThread().getName());
                System.out.println(executor.getActiveCount());
            });
        }
        executor.shutdown(); // 终止线程池
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished all threads");
    }
}


// 线程工作,设置线程名称
class NamingThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNum = new AtomicInteger();
    private final ThreadFactory delegate;
    private final String name;

    public NamingThreadFactory(ThreadFactory delegate, String name) {
        this.delegate = delegate;
        this.name = name;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread t = delegate.newThread(r);
        t.setName(name + "[#" + threadNum.incrementAndGet() + "]");
        return t;
    }
}