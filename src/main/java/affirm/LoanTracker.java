package affirm;

import java.util.LinkedList;
import java.util.Queue;

class LoanTracker {

    // Define a loan record containing a timestamp and the loan volume
    private static class Loan {
        long timestamp;
        int volume;

        public Loan(long timestamp, int volume) {
            this.timestamp = timestamp;
            this.volume = volume;
        }
    }

    private Queue<Loan> loanQueue;
    private int totalVolume;

    public LoanTracker() {
        loanQueue = new LinkedList<>();
        totalVolume = 0;
    }

    // Store the loan volume with the current timestamp
    public void storeLoan(int volume) {
        long currentTimestamp = System.currentTimeMillis(); // Get current timestamp in milliseconds
        Loan loan = new Loan(currentTimestamp, volume);
        loanQueue.add(loan);   // Add new loan to the queue
        totalVolume += volume; // Increment total volume
    }

    // Get the total loan volume from the last hour
    public int getLoanVolume() {
        long oneHourAgo = System.currentTimeMillis() - 3600 * 1000; // 1 hour in milliseconds

        // Remove loans that are older than one hour and adjust the total volume
        while (!loanQueue.isEmpty() && loanQueue.peek().timestamp < oneHourAgo) {
            Loan expiredLoan = loanQueue.poll();  // Remove the old loan
            totalVolume -= expiredLoan.volume;    // Subtract the volume of the old loan
        }

        return totalVolume;
    }

    public static void main(String[] args) {
        LoanTracker loanTracker = new LoanTracker();

        // Simulate storing some loan volumes
        loanTracker.storeLoan(100);
        loanTracker.storeLoan(200);

        // Simulate a delay (for example, 30 minutes) to show the getLoanVolume function
        try {
            Thread.sleep(30 * 60 * 1000); // 30 minutes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Store more loans after the delay
        loanTracker.storeLoan(300);
        loanTracker.storeLoan(400);

        // Get the total loan volume from the last hour
        int loanVolume = loanTracker.getLoanVolume();
        System.out.println("Total loan volume from the last hour: " + loanVolume); // Expected output: 700
    }
}
