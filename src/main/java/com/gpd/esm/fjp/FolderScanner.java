package com.gpd.esm.fjp;

import java.io.File;
import java.util.concurrent.RecursiveTask;

public class FolderScanner extends RecursiveTask<FolderScanner.FolderStats> {
    private final File folder;

    public FolderScanner(File folder) {
        this.folder = folder;
    }

    @Override
    protected FolderStats compute() {
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("Scanning interrupted.");
            return new FolderStats(0, 0, 0);
        }

        File[] files = folder.listFiles();

        if (files != null) {
            int filesCount = 0;
            int foldersCount = 0;
            long totalSize = 0;

            for (File file : files) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Scanning interrupted.");
                    return new FolderStats(filesCount, foldersCount, totalSize);
                }

                if (file.isFile()) {
                    filesCount++;
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    foldersCount++;

                    FolderScanner subtask = new FolderScanner(file);
                    subtask.fork();
                    FolderStats subResult = subtask.join();

                    filesCount += subResult.fileCount();
                    foldersCount += subResult.folderCount();
                    totalSize += subResult.size();
                }
            }
            return new FolderStats(filesCount, foldersCount, totalSize);
        }
        return new FolderStats(0, 0, 0);
    }

    public record FolderStats(int fileCount, int folderCount, long size) {

        @Override
        public String toString() {
            return "File count: " + fileCount +
                    "\nFolder count: " + folderCount +
                    "\nSize: " + size + " bytes";
        }
    }
}