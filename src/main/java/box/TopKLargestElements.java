package box;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class TopKLargestElements {

    public static List<Integer> findTopK(String rootDir, int k) throws IOException {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        // Step 1: Walk through the root directory
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            paths.filter(Files::isRegularFile).forEach(files::add);
        }

        // Step 2: Process each file
        for (Path filePath : files) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int number = Integer.parseInt(line.trim());
                    if (minHeap.size() < k) {
                        minHeap.add(number);
                    } else if (number > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.add(number);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error processing file " + filePath + ": " + e.getMessage());
            }
        }

        // Step 3: Convert result to list and sort
        List<Integer> result = new ArrayList<>(minHeap);
        result.sort(Collections.reverseOrder()); // Sort in descending order if needed
        return result;
    }

    public static void main(String[] args) {
        String rootDirectory = "/path/to/root/directory";
        int k = 10; // Example value for k
        try {
            List<Integer> topK = findTopK(rootDirectory, k);
            System.out.println("Top " + k + " largest elements:");
            topK.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

