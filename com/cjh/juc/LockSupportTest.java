package com.cjh.juc;

/**
 * @ClassName LockSupportTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 16:15
 * @Version 1.0
 */
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        MythreadPark myThread = new MythreadPark(Thread.currentThread());
        synchronized (myThread) {
            myThread.start();
            Thread.sleep(3000); // �����߳�
            System.out.println("before wait");
            myThread.wait(); // �ͷ��� ������״̬ �ȴ�����notify(), notify()��������Ҫ��wait֮ǰ����,Ҫ��Ȼ�̻߳�һֱ��������״̬
            System.out.println("after wait");
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        synchronized (this) {
            System.out.println("before notify");
//            notify();
            System.out.println("after notify");
        }
    }
}



