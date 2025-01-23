package amazon.dp;

import java.util.*;

public class PhoneLetter {

    List<String> result = new ArrayList<>();
    Map<Character, String> map = new HashMap<>();

    public List<String> letterCombinations(String digits) {

        if (digits.equals("") || digits == null) {
            return result;
        }

        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        backtrack(digits, new StringBuilder(), 0);

        return result;
    }


    public void backtrack(String digits, StringBuilder sb, int index){

        if (index == digits.length()){
            result.add(sb.toString());
            return;
        }

        String letters = map.get(digits.charAt(index));
        for (char c: letters.toCharArray()){
            sb.append(c);
            backtrack(digits, sb,  index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
