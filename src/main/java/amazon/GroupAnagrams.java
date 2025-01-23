package amazon;
import java.util.*;
public class GroupAnagrams {
    Map<String, List<String>> map = new HashMap<>();

    public List<List<String>> groupAnagrams(String[] strs) {
        for (String str : strs) {
            anagrams(str);
        }
        return new ArrayList<>(map.values());
    }

    public void anagrams(String str) {
        char[] arr = str.toCharArray();
        Arrays.sort(arr);
        String arrString = Arrays.toString(arr);
        if (!map.containsKey(arrString)) {
            map.put(arrString, new ArrayList<>());
        }
        map.get(arrString).add(str);
    }
}
