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
    private static final int THRESHOLD = 2; // 阈值
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
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待子任务执行完,并得到其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;

        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTest task = new ForkJoinTest(1,8);
        // 执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }
}
