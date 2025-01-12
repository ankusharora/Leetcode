package affirm;

import java.util.*;

public class LetterCoOccurrence {

    public static Map<Character, List<Character>> findCoOccurrence(List<String> words) {
        // A map to track the co-occurrence of letters
        Map<Character, Map<Character, Integer>> letterToCoOccurrences = new HashMap<>();

        // Iterate through each word
        for (String word : words) {
            Set<Character> uniqueLetters = new HashSet<>();

            // Collect unique letters in the word
            for (char letter : word.toCharArray()) {
                uniqueLetters.add(letter);
            }

            // For each pair of letters in the word, update co-occurrence counts
            for (char letter1 : uniqueLetters) {
                for (char letter2 : uniqueLetters) {
                    if (letter1 != letter2) {
                        letterToCoOccurrences
                                .computeIfAbsent(letter1, k -> new HashMap<>())
                                .merge(letter2, 1, Integer::sum);
                    }
                }
            }
        }

        // Now, build the final result map based on the co-occurrence counts
        Map<Character, List<Character>> result = new HashMap<>();
        for (Map.Entry<Character, Map<Character, Integer>> entry : letterToCoOccurrences.entrySet()) {
            char letter = entry.getKey();
            Map<Character, Integer> coOccurrenceCounts = entry.getValue();

            // Find the maximum co-occurrence count
            int maxCount = coOccurrenceCounts.values().stream().max(Integer::compare).orElse(0);

            // Collect all letters that appear with the maximum count
            List<Character> mostCommonLetters = new ArrayList<>();
            for (Map.Entry<Character, Integer> coEntry : coOccurrenceCounts.entrySet()) {
                if (coEntry.getValue() == maxCount) {
                    mostCommonLetters.add(coEntry.getKey());
                }
            }

            result.put(letter, mostCommonLetters);
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("abc", "bcd", "cde");
        Map<Character, List<Character>> coOccurrenceMap = findCoOccurrence(words);

        // Print the result
        for (Map.Entry<Character, List<Character>> entry : coOccurrenceMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

