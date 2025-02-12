package LinkedIn;

import java.util.*;

public class PhoneNumberToWords {
    private static final Map<Character, Character> DIGIT_TO_LETTER_MAP = new HashMap<>();

    static {
        String[] mappings = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        for (int i = 2; i <= 9; i++) {
            for (char c : mappings[i].toCharArray()) {
                DIGIT_TO_LETTER_MAP.put(c, (char) ('0' + i));
            }
        }
    }

    private static String wordToNumber(String word) {
        StringBuilder number = new StringBuilder();
        for (char c : word.toLowerCase().toCharArray()) {
            if (DIGIT_TO_LETTER_MAP.containsKey(c)) {
                number.append(DIGIT_TO_LETTER_MAP.get(c));
            } else {
                return ""; // Invalid character in word
            }
        }
        return number.toString();
    }

    public static List<String> findMatchingWords(String phoneNumber, List<String> knownWords) {
        List<String> matchingWords = new ArrayList<>();
        for (String word : knownWords) {
            if (wordToNumber(word).equals(phoneNumber)) {
                matchingWords.add(word);
            }
        }
        return matchingWords;
    }

    public static void main(String[] args) {
        List<String> KNOWN_WORDS = Arrays.asList("careers", "linkedin", "hiring", "interview", "linkedgo");

        String phoneNumber1 = "2273377";
        System.out.println("Matches for " + phoneNumber1 + ": " + findMatchingWords(phoneNumber1, KNOWN_WORDS));

        String phoneNumber2 = "54653346";
        System.out.println("Matches for " + phoneNumber2 + ": " + findMatchingWords(phoneNumber2, KNOWN_WORDS));
    }
}
