package com.gpd.esm.runner;

import com.gpd.esm.fjp.Applyer;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ApplyerRunner {
    public static void main(String[] args) {
        double[] array = arrayGenerator();
        System.out.println(compareSquareSumProcessing(array));
    }

    private static TimeEstimator compareSquareSumProcessing(double[] array) {
        long start = System.currentTimeMillis();
        sequentialSumOfSquares(array);
        long end = System.currentTimeMillis();
        long sequentialDuration = end - start;

        start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        sumOfSquares(pool, array);
        end = System.currentTimeMillis();
        long parallelDuration = end - start;

        return new TimeEstimator(sequentialDuration, parallelDuration);
    }

    private static double[] arrayGenerator() {
        double[] array = new double[500_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextDouble() * 1000;
        }
        return array;
    }

    private static double sequentialSumOfSquares(double[] array) {
        double sum = 0;
        for (double x : array)
            sum += x * x;
        return sum;
    }

    private static double sumOfSquares(ForkJoinPool pool, double[] array) {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        return a.result;
    }

    record TimeEstimator(long sequentialRun, long parallelRun) {}
}
