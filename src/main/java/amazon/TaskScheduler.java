package amazon;

import java.util.Arrays;

public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        int[] charMap = new int[26];

        for (char c : tasks) {
            charMap[c - 'A']++;
        }

        Arrays.sort(charMap); // Most frequent vaues at the end

        int maxfre = charMap[25] - 1; // most frequent tasks

        int idleSlots = maxfre * n;

        for (int i = 24; i >= 0; i--){
            idleSlots -= Math.min(charMap[i], maxfre);
        }

        return idleSlots > 0 ? idleSlots + tasks.length : tasks.length;

    }
}
