package com.gpd.esm;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class PCSemaphore {
    private final Set<Integer> channel;
    private final Semaphore semaphore;

    public PCSemaphore(int bufferSize) {
        channel = new HashSet<>(bufferSize);
        semaphore = new Semaphore(bufferSize);
    }

    public boolean put(int value) throws InterruptedException {
        semaphore.acquire();
        System.out.println("Queue length: " + semaphore.availablePermits());
        boolean wasAdded = false;
        try {
            wasAdded = channel.add(value);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean get(Integer integer) {
        boolean wasRemoved = channel.remove(integer);
        if (wasRemoved)
            semaphore.release();
        return wasRemoved;
    }
}
