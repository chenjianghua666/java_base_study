package com.cjh.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ThreadConditionDemo
 * @Description
 * @Author Administrator
 * @Date 2022/8/4 16:54
 * @Version 1.0
 */
public class ThreadConditionDemo {
    public static void main(String[] args) throws InterruptedException {

        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.start();

        Thread.sleep(3000);

        service.signal();

    }

    static public class MyService {

        private Lock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();

        public void await() {
            lock.lock();
            try {
                System.out.println(" awaitʱ��Ϊ" + System.currentTimeMillis());
                condition.await();
                System.out.println("����condition.await()����֮�����䣬condition.signal()����֮���Ҳű�ִ��");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signal() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("signalʱ��Ϊ" + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(3000);
                System.out.println("����condition.signal()����֮������");
            } finally {
                lock.unlock();
            }
        }
    }

    static public class ThreadA extends Thread {

        private MyService service;

        public ThreadA(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.await();
        }
    }
}




