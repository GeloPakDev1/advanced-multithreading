package com.gpd.esm.runner;

import com.gpd.esm.fjp.FolderScanner;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class FolderScannerRunner {
    public static final String FOLDER_PATH = "src";

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        System.out.println("Scanning started.");
        FolderScanner task = new FolderScanner(new File(FOLDER_PATH));
        pool.execute(task);

        while (!task.isDone()) {
            System.out.print("\rScanning...");
        }

        System.out.println(task.join());
        System.out.println("\nScanning finished.");
        pool.shutdown();
    }
}