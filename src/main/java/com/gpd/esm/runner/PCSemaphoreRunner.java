package com.gpd.esm.runner;

import com.gpd.esm.PCSemaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class PCSemaphoreRunner {
    public static final int BUFFER_SIZE = 5;
    private static final Logger logger = Logger.getLogger(PCSemaphoreRunner.class.getName());
    private static final PCSemaphore channel = new PCSemaphore(BUFFER_SIZE);
    public static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Runnable producerTask = () -> {
            try {
                for (int i = 0; i < 10; i++) {
                    channel.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(1000); // Simulating production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Thread interrupted" + e.getCause());
            }
        };
        Runnable consumerTask = () -> {
            try {
                for (int i = 0; i < 10; i++) {
                    boolean removed = channel.get(i);
                    if (removed) {
                        System.out.println("Consumed: " + i);
                    } else {
                        System.out.println("Failed to consume: " + i);
                    }
                    Thread.sleep(2000); // Simulating consumption time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Thread interrupted" + e.getCause());
            }
        };

        executor.submit(producerTask);
        executor.submit(consumerTask);

        executor.shutdown();
    }
}