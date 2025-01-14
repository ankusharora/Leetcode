package box;

import java.util.*;


/**
 *
 * TIME  BFS - O(N⋅L⋅26)=O(N⋅L)
 * BACK TRACK - O(P⋅k)   P be the number of shortest paths
 * Total O(N⋅L+P⋅k)
 *
 *
 *
 * SPACE:
 *
 * O(N⋅L) BFS
 * O(P⋅k⋅L)
 *
 */
public class WordLadder2 {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord))
            return result;

        // Step 1: BFS to build the graph
        Map<String, List<String>> graph = new HashMap<>();
        if (!bfs(beginWord, endWord, wordSet, graph))
            return result;

        // Step 2: Backtrack to find all paths
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        backtrack(beginWord, endWord, graph, path, result);

        return result;
    }

    private boolean bfs(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> graph) {
        Set<String> currentLevel = new HashSet<>();
        currentLevel.add(beginWord);

        boolean found = false;
        Set<String> visited = new HashSet<>();

        while (!currentLevel.isEmpty()) {
            Set<String> nextLevel = new HashSet<>();

            for (String word : currentLevel) {
                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    char originalChar = chars[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar)
                            continue;

                        chars[i] = c;
                        String newWord = new String(chars);

                        if (wordSet.contains(newWord)) {
                            nextLevel.add(newWord);
                            graph.computeIfAbsent(word, k -> new ArrayList<>()).add(newWord);

                            if (newWord.equals(endWord)) {
                                found = true;
                            }
                        }
                    }
                    chars[i] = originalChar;
                }
            }

            visited.addAll(currentLevel);
            wordSet.removeAll(nextLevel);
            currentLevel = nextLevel;

            if (found)
                break;
        }

        return found;
    }

    private void backtrack(String currentWord, String endWord, Map<String, List<String>> graph,
                           List<String> path, List<List<String>> result) {
        if (currentWord.equals(endWord)) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (!graph.containsKey(currentWord))
            return;

        for (String neighbor : graph.get(currentWord)) {
            if (path.contains(neighbor))
                continue; // Avoid revisiting nodes in the current path
            path.add(neighbor);
            backtrack(neighbor, endWord, graph, path, result);
            path.remove(path.size() - 1); // Backtrack
        }
    }

}
