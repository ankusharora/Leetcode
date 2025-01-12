package Docusign;

import java.util.HashSet;
import java.util.Set;

public class OptimalPartition {

    /**
     *
     * Input: s = "abacaba"
     * Output: 4
     * Explanation:
     * Two possible partitions are ("a","ba","cab","a") and ("ab","a","ca","ba").
     * It can be shown that 4 is the minimum number of substrings needed.
     *
     *
     * @param s
     * @return
     */

    public int partitionString(String s) {
        int ans = 1;
        Set<Character> set = new HashSet<>();

        for (char c: s.toCharArray()){
            if (set.contains(c)){
                ans++;
                set.clear();
            }

            set.add(c);

        }

        return ans;
    }

}
