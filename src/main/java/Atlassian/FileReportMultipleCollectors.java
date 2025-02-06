package Atlassian;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// File class representing a file
class File2 {
    private String name;
    private int size;
    private List<String> collections; // List of collections for this file

    public File2(String name, int size, List<String> collections) {
        this.name = name;
        this.size = size;
        this.collections = collections;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public List<String> getCollections() {
        return collections;
    }
}

// Collection class representing a collection
class Collection2 {
    private String name;
    private AtomicInteger totalSize;

    public Collection2(String name) {
        this.name = name;
        this.totalSize = new AtomicInteger(0);
    }

    public String getName() {
        return name;
    }

    public int getTotalSize() {
        return totalSize.get();
    }

    public void addFile(int fileSize) {
        totalSize.addAndGet(fileSize);
    }
}

// FileSystemReport class to manage files and collections
class FileSystemReportMultiple {
    private List<File2> files; // Use File2 instead of File
    private Map<String, Collection2> collections; // Use Collection2 instead of Collection
    private AtomicInteger totalSize;

    public FileSystemReportMultiple() {
        files = new CopyOnWriteArrayList<>(); // Thread-safe list
        collections = new ConcurrentHashMap<>(); // Thread-safe map
        totalSize = new AtomicInteger(0);
    }

    // Add a file to the system
    public void addFile(File2 file) {
        files.add(file);
        totalSize.addAndGet(file.getSize());

        // Update the total size for each collection associated with the file
        List<String> fileCollections = file.getCollections();
        if (fileCollections != null && !fileCollections.isEmpty()) {
            for (String collectionName : fileCollections) {
                collections.computeIfAbsent(collectionName, k -> new Collection2(collectionName))
                        .addFile(file.getSize());
            }
        }
    }

    // Get the total size of all files
    public int getTotalSize() {
        return totalSize.get();
    }

    // Get the top N collections by size
    public List<String> getTopNCollections(int N) {
        // Create a list of collections sorted by total size in descending order
        List<Collection2> sortedCollections = new ArrayList<>(collections.values());
        sortedCollections.sort((a, b) -> b.getTotalSize() - a.getTotalSize());

        // Extract the top N collections
        List<String> topNCollections = new ArrayList<>();
        for (int i = 0; i < Math.min(N, sortedCollections.size()); i++) {
            topNCollections.add(sortedCollections.get(i).getName());
        }

        return topNCollections;
    }
}

// Main class to test the multi-threaded implementation with multiple collections per file
public class FileReportMultipleCollectors {
    public static void main(String[] args) throws InterruptedException {
        // Create an instance of the report generator
        FileSystemReportMultiple report = new FileSystemReportMultiple();

        // Create a thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // List of files to add (each file can belong to multiple collections)
        List<File2> filesToAdd = Arrays.asList(
                new File2("file1.txt", 100, Arrays.asList("collection1", "collection2")),
                new File2("file2.txt", 200, Arrays.asList("collection1")),
                new File2("file3.txt", 200, Arrays.asList("collection1", "collection3")),
                new File2("file4.txt", 300, Arrays.asList("collection2")),
                new File2("file5.txt", 10, Collections.emptyList()) // No collection
        );

        // Submit tasks to the thread pool
        for (File2 file : filesToAdd) {
            executor.submit(() -> report.addFile(file));
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Get the total size of all files
        System.out.println("Total size of all files: " + report.getTotalSize());

        // Get the top 3 collections by size
        List<String> topCollections = report.getTopNCollections(3);
        System.out.println("Top 3 collections: " + topCollections);
    }
}