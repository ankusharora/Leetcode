package box;

import java.util.Arrays;

public class BitFlippingExample {
    public static void main(String[] args) {
        int num = 10;  // Binary: 1010
        int position = 2; // The bit position to flip (starting from 0)

        // Create a bitmask with a 1 in the position to flip
        int bitmask = 1 << position;

        // Flip the bit using XOR
        int result = num ^ bitmask;

        System.out.println("Original number: " + num);  // Output: 10
        System.out.println("Flipped number: " + result); // Output: 14 (Binary: 1110)
    }
}