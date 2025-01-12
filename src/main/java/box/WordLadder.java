package box;

import java.util.*;

public class WordLadder {

    public int ladderLength(String beginWord, String endWord, List<String> wordList){

        Set<String> set = new HashSet<>();
        wordList.forEach(set::add);

        if (!set.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;
        while (!queue.isEmpty()){
            String currentWord = queue.poll();
            char[] arr = currentWord.toCharArray();

            for (int j = 0; j < arr.length; j++){
                char originalChar = arr[j];

                for (char c = 'a'; c <= 'z'; c++){
                    if ( c == arr[j]) continue;

                    arr[j] = c;
                    String newWord = String.valueOf(arr);

                    if (newWord.equals(endWord)){
                        return level +1;
                    }

                    if (set.contains(newWord)){
                        queue.offer(newWord);
                        set.remove(newWord);
                    }

                }

                arr[j] = originalChar;
            }

            level++;
        }

        return 0;

    }

}
