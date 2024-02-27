package com.gpd.esm.runner;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PCBlockingQueueRunner {
    private static final int BUFFER_SIZE = 5;
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        executor.submit(PCBlockingQueueRunner::producer);
        executor.submit(PCBlockingQueueRunner::consumer);

        executor.shutdown();
    }

    private static void producer() {
        var random = new Random();
        while (true) {
            try {
                var number = random.nextInt(100);
                buffer.put(number);
                System.out.println("Produced: " + number + "\033[0;31m");
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void consumer() {
        var random = new Random();
        while (true) {
            try {
                var number = buffer.take();
                System.out.println("Consumed: " + number + "\033[0;32m");
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
