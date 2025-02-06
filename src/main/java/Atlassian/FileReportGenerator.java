package Atlassian;

import java.util.*;

/**
 * CopyOnWriteArrayList is used for the files list to ensure thread safety.
 *
 * ConcurrentHashMap is used for the collections map to ensure thread safety.
 *
 * AtomicInteger is used for totalSize to ensure atomic updates.
 *
 * An ExecutorService with a fixed thread pool is used to add files concurrently.
 *
 * Each file addition task is submitted to the thread pool, and the tasks are executed concurrently.
 */

// File class representing a file
class File1 {
    private String name;
    private int size;
    private String collection;

    public File1(String name, int size, String collection) {
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
class Collection1 {
    private String name;
    private int totalSize;

    public Collection1(String name) {
        this.name = name;
        this.totalSize = 0;
    }

    public String getName() {
        return name;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void addFile(int fileSize) {
        totalSize += fileSize;
    }
}

// FileSystemReport class to manage files and collections
public class FileReportGenerator {
    public static void main(String[] args) {
        // Create an instance of the report generator
        FileReportGenerator report = new FileReportGenerator();

        // Add files to the system
        report.addFile(new File("file1.txt", 100, null)); // No collection
        report.addFile(new File("file2.txt", 200, "collection1"));
        report.addFile(new File("file3.txt", 200, "collection1"));
        report.addFile(new File("file4.txt", 300, "collection2"));
        report.addFile(new File("file5.txt", 10, null)); // No collection

        // Get the total size of all files
        System.out.println("Total size of all files: " + report.getTotalSize());

        // Get the top 2 collections by size
        List<String> topCollections = report.getTopNCollections(2);
        System.out.println("Top 2 collections: " + topCollections);
    }


    private List<File> files;
    private Map<String, Collection> collections;
    private int totalSize;

    public FileReportGenerator() {
        files = new ArrayList<>();
        collections = new HashMap<>();
        totalSize = 0;
    }

    // Add a file to the system
    public void addFile(File file) {
        files.add(file);
        totalSize += file.getSize();

        // If the file belongs to a collection, update the collection's total size
        String collectionName = file.getCollection();
        if (collectionName != null && !collectionName.isEmpty()) {
            collections.putIfAbsent(collectionName, new Collection(collectionName));
            collections.get(collectionName).addFile(file.getSize());
        }
    }

    // Get the total size of all files
    public int getTotalSize() {
        return totalSize;
    }

    // Get the top N collections by size
    public List<String> getTopNCollections(int N) {
        // Create a max-heap (PriorityQueue) based on total size in descending order
        PriorityQueue<Collection> maxHeap = new PriorityQueue<>(
                (a, b) -> b.getTotalSize() - a.getTotalSize()
        );

        // Add all collections to the max-heap
        maxHeap.addAll(collections.values());

        // Extract the top N collections
        List<String> topNCollections = new ArrayList<>();
        for (int i = 0; i < N && !maxHeap.isEmpty(); i++) {
            topNCollections.add(maxHeap.poll().getName());
        }

        return topNCollections;
    }

}

