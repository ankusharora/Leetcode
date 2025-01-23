package amazon.string;
import java.util.*;
public class LongestSubstringWithoutRepeatingChars {
    public int lengthOfLongestSubstring(String s) {

        Set<Character> ch = new HashSet<>();
        int maxSize = 0;
        int i = 0;
        int j = 0;

        while (j < s.length()){
            if (!ch.contains(s.charAt(j))){
                ch.add(s.charAt(j));
                j++;
                maxSize = Math.max(maxSize, ch.size());
            } else {
                ch.remove(s.charAt(i));
                i++;
            }
        }

        return maxSize;
    }
}
