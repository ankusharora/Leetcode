package amazon.string;
import java.util.*;
/**
 * Use a stack to track unmatched opening brackets.
 * Push opening brackets onto the stack.
 * For closing brackets, check if the stack is empty or the top doesn't match; if so, return false.
 * Pop the stack for matching brackets.
 * Return true if the stack is empty at the end; otherwise, return false.
 */
public class ValidParanthesis {

    public boolean isValid(String s) {
        if (s.length() < 2) {
            return false;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Stack<Character> stack = new Stack<>();

        for(char c : s.toCharArray()) {
            if(c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if(stack.isEmpty() || stack.peek() != map.get(c) ) {
                    return false;
                }
                stack.pop();
            }
        }

        return stack.isEmpty();
    }
}
