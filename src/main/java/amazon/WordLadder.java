package amazon;
import java.util.*;
public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        Set<String> wordSet = new HashSet<>();

        for (String word : wordList) {
            wordSet.add(word);
        }

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                char[] wordArr = currentWord.toCharArray();
                for (int j = 0; j < wordArr.length; j++){
                    char originalChar = wordArr[j];

                    for (char c = 'a'; c <= 'z'; c++){
                        if (wordArr[j] == c) continue;

                        wordArr[j] = c;
                        String newWord = String.valueOf(wordArr);

                        if (newWord.equals(endWord)){
                            return level + 1;
                        }

                        if (wordSet.contains(newWord)){
                            queue.offer(newWord);
                            wordSet.remove(newWord);
                        }
                    }

                    wordArr[j] = originalChar;
                }
            }

            level++;

        }

        return 0;

    }
}
