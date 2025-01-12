package box;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TopKWordsInAllFiles {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TopKWords <directory_path> <K>");
            return;
        }

        String dirPath = args[0];
        int K = Integer.parseInt(args[1]);

        try {
            Map<String, Integer> wordCounts = new HashMap<>();
            Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .forEach(file -> processFile(file, wordCounts));

            List<String> topKWords = getTopKWords(wordCounts, K);
            topKWords.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(Path file, Map<String, Integer> wordCounts) {
        try {
            Files.lines(file).forEach(line -> {
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordCounts.merge(word, 1, Integer::sum); // Count word frequency
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getTopKWords(Map<String, Integer> wordCounts, int K) {
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(K, (a, b) -> a.getValue() - b.getValue());

        wordCounts.entrySet().forEach(entry -> {
            if (minHeap.size() < K) {
                minHeap.offer(entry);
            } else if (minHeap.peek().getValue() < entry.getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        });

        List<String> topKWords = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            topKWords.add(minHeap.poll().getKey());
        }

        Collections.reverse(topKWords); // Optional, if you want the top K in descending order
        return topKWords;
    }
}
