package com.gpd.esm.fjp;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
    private final int number;

    public Fibonacci(int number) {
        this.number = number;
    }

    @Override
    protected Integer compute() {
        if (number <= 13) {
            return solveSequentially(number);
        }
        Fibonacci fibonacci_1 = new Fibonacci(number - 1);
        fibonacci_1.fork();
        Fibonacci fibonacci_2 = new Fibonacci(number - 2);
        return fibonacci_2.compute() + fibonacci_1.join();
    }

    private int solveSequentially(int n) {
        if (n <= 1)
            return n;
        int prev = 0;
        int curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
