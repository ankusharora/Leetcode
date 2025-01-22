package amazon.string;

/**
 *
 * Start from the last digits of both strings and a carry initialized to 0.
 * Add digits from both strings and the carry to calculate the current total.
 * Extract the current digit as total % 10 and update the carry as total / 10.
 * Append the current digit to the result string.
 * Continue until both strings are fully traversed, and no carry remains.
 * Reverse the result string and return it.
 */
public class AddStrings {
    public String addStrings(String num1, String num2) {
        int n1 = num1.length() - 1;
        int n2 = num2.length() - 1;
        int carry = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (n1 >= 0 || n2 >= 0 || carry != 0) {
            int total = carry;
            if (n1 >= 0) {
                total += num1.charAt(n1) - '0';
                n1--;
            }
            if (n2 >= 0) {
                total += num2.charAt(n2) - '0';
                n2--;
            }

            int digit = total % 10;
            carry = total / 10;
            stringBuilder.append(digit);
        }

        return stringBuilder.reverse().toString();
    }
}
