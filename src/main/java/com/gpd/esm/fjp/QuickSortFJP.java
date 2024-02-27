package com.gpd.esm.fjp;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class QuickSortFJP extends RecursiveAction {
    private final int[] array;
    private final int low;
    private final int high;

    public QuickSortFJP(int[] array) {
        this(array, 0, array.length - 1);
    }

    public QuickSortFJP(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        //Base case for recursion
        if (high - low + 1 < 100) {
            Arrays.sort(array, low, high + 1);
        } else {
            int pivotIndex = partition(array, low, high);
            QuickSortFJP leftSide = new QuickSortFJP(array, low, pivotIndex - 1);
            QuickSortFJP rightSide = new QuickSortFJP(array, pivotIndex + 1, high);
            leftSide.fork();
            rightSide.compute();
            leftSide.join();
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivotIndex = new Random().nextInt(high - low + 1) + low;
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}