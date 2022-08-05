package com.cjh.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @ClassName ForkJoinTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/4 18:05
 * @Version 1.0
 */
public class ForkJoinTest extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2; // ��ֵ
    private int start;
    private int end;
    public ForkJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i<=end; i++) {
                sum += i;
            }
        }else {
            int middle = (start + end) / 2;
            System.out.printf("split== %d~%d, %d~%d%n", start, middle, middle, end);

            ForkJoinTest leftTask = new ForkJoinTest(start, middle);
            ForkJoinTest rightTask = new ForkJoinTest(middle + 1, end);
            // ִ��������
            leftTask.fork();
            rightTask.fork();
            // �ȴ�������ִ����,���õ�����
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // �ϲ�������
            sum = leftResult + rightResult;

        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTest task = new ForkJoinTest(1,8);
        // ִ��һ������
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }
}
