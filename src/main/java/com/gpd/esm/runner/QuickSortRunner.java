package com.gpd.esm.runner;

import com.gpd.esm.fjp.QuickSortFJP;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class QuickSortRunner {
    private static final int ARRAY_SIZE = 10_000_000;

    public static void main(String[] args) {
        var array = arrayGenerator(ARRAY_SIZE);
        ForkJoinPool pool = new ForkJoinPool();
        QuickSortFJP quickSortFJP = new QuickSortFJP(array);
        pool.invoke(quickSortFJP);
        System.out.println(Arrays.toString(array));
    }

    private static int[] arrayGenerator(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextInt(size - i + 100);
        }
        return array;
    }
}