package box;


import java.util.*;

public class WordLadder2Optimized {

    public List<String> findShortestLadder(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return Collections.emptyList();

        Queue<List<String>> queue = new LinkedList<>();
        queue.offer(new ArrayList<>(List.of(beginWord)));

        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<String> visitedThisLevel = new HashSet<>();

            for (int i = 0; i < size; i++) {
                List<String> currentPath = queue.poll();
                String currentWord = currentPath.get(currentPath.size() - 1);

                char[] charArray = currentWord.toCharArray();
                for (int j = 0; j < charArray.length; j++) {
                    char originalChar = charArray[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;

                        charArray[j] = c;
                        String newWord = new String(charArray);

                        if (newWord.equals(endWord)) {
                            currentPath.add(newWord);
                            return currentPath; // Return the first valid sequence
                        }

                        if (wordSet.contains(newWord)) {
                            visitedThisLevel.add(newWord);
                            List<String> newPath = new ArrayList<>(currentPath);
                            newPath.add(newWord);
                            queue.offer(newPath);
                        }
                    }
                    charArray[j] = originalChar; // Restore original character
                }
            }
            wordSet.removeAll(visitedThisLevel); // Avoid revisiting words
        }

        return Collections.emptyList(); // If no path is found
    }

    public static void main(String[] args) {
        WordLadder2Optimized solver = new WordLadder2Optimized();

        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

        List<String> result = solver.findShortestLadder(beginWord, endWord, wordList);
        System.out.println(result); // Example output: ["hit", "hot", "dot", "dog", "cog"]
    }
}
