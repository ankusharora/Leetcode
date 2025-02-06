package Atlassian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// File class representing a file
class File {
    private String name;
    private int size;
    private String collection;

    public File(String name, int size, String collection) {
        this.name = name;
        this.size = size;
        this.collection = collection;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getCollection() {
        return collection;
    }
}

// Collection class representing a collection
class Collection {
    private String name;
    private AtomicInteger totalSize;

    public Collection(String name) {
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
class FileSystemReport {
    private List<File> files;
    private Map<String, Collection> collections;
    private AtomicInteger totalSize;

    public FileSystemReport() {
        files = new CopyOnWriteArrayList<>(); // Thread-safe list
        collections = new ConcurrentHashMap<>(); // Thread-safe map
        totalSize = new AtomicInteger(0);
    }

    // Add a file to the system
    public void addFile(File file) {
        files.add(file);
        totalSize.addAndGet(file.getSize());

        // If the file belongs to a collection, update the collection's total size
        String collectionName = file.getCollection();
        if (collectionName != null && !collectionName.isEmpty()) {
            collections.computeIfAbsent(collectionName, k -> new Collection(collectionName))
                    .addFile(file.getSize());
        }
    }

    // Get the total size of all files
    public int getTotalSize() {
        return totalSize.get();
    }

    // Get the top N collections by size
    public List<String> getTopNCollections(int N) {
        // Create a list of collections sorted by total size in descending order
        List<Collection> sortedCollections = new ArrayList<>(collections.values());
        sortedCollections.sort((a, b) -> b.getTotalSize() - a.getTotalSize());

        // Extract the top N collections
        List<String> topNCollections = new ArrayList<>();
        for (int i = 0; i < Math.min(N, sortedCollections.size()); i++) {
            topNCollections.add(sortedCollections.get(i).getName());
        }

        return topNCollections;
    }
}

// Main class to test the multi-threaded implementation
public class FileReportMultiThreaded {
    public static void main(String[] args) throws InterruptedException {
        // Create an instance of the report generator
        FileSystemReport report = new FileSystemReport();

        // Create a thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // List of files to add
        List<File> filesToAdd = Arrays.asList(
                new File("file1.txt", 100, null), // No collection
                new File("file2.txt", 200, "collection1"),
                new File("file3.txt", 200, "collection1"),
                new File("file4.txt", 300, "collection2"),
                new File("file5.txt", 10, null) // No collection
        );

        // Submit tasks to the thread pool
        for (File file : filesToAdd) {
            executor.submit(() -> report.addFile(file));
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Get the total size of all files
        System.out.println("Total size of all files: " + report.getTotalSize());

        // Get the top 2 collections by size
        List<String> topCollections = report.getTopNCollections(2);
        System.out.println("Top 2 collections: " + topCollections);
    }
}
