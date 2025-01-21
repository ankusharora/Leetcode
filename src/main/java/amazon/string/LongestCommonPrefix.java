package amazon.string;

/**
 Initialize the prefix as the first string in the array.
 Compare the prefix with each string in the array.
 If the current string doesn't start with the prefix, shorten the prefix by removing its last character.
 Stop when the prefix is empty or all strings match the prefix.
 Return the final prefix.
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty())
                    return "";
            }
        }

        return prefix;
    }
}
