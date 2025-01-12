package box;

public class NumberOf1Bits {

    public static int hammingWeight(int n) {
        /**
         * 1010 (binary for 10)
         * & 1001 (binary for 9)
         * ----
         * 1000 (result in binary = 8)
         */

        int sum = 0;
        while (n != 0) {
            sum++;
            n &= (n - 1);
        }

        return sum;
    }

    public static void main(String[] args){
        System.out.println(hammingWeight(14));
    }
}
