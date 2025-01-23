package amazon.stack;
import java.util.*;

/**
 * Use a stack to track the currently running function.
 * For a start log, calculate the elapsed time for the top function, update its exclusive time, and push the new function onto the stack.
 * For an end log, calculate the elapsed time for the top function (inclusive of the end timestamp), update its exclusive time, and pop it from the stack.
 * Update prevTime after processing each log.
 * Return the exclusive times of all functions after processing all logs.
 */
public class ExclusiveTime {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] exclusiveTime = new int[n];
        Stack<Integer> stack = new Stack<>();
        int prevTime = 0;

        for (String log : logs) {
            String[] parts = log.split(":");
            int functionId = Integer.parseInt(parts[0]);
            String action = parts[1];
            int timestamp = Integer.parseInt(parts[2]);

            if (!stack.isEmpty()) {
                exclusiveTime[stack.peek()] += timestamp - prevTime;
            }
            if (action.equals("start")) {
                stack.push(functionId);
            } else {
                exclusiveTime[stack.pop()] += 1;
                timestamp += 1;
            }

            prevTime = timestamp;
        }

        return exclusiveTime;
    }

    public Map<String, Integer> exclusiveTime(List<String> logs) {
        Map<String, Integer> result = new HashMap<>();
        Stack<String> stack = new Stack<>();
        int prevTime = 0;

        for (String log : logs) {
            String[] parts = log.split(" ");
            String func = parts[0];
            int time = Integer.parseInt(parts[1]);

            if (!stack.isEmpty()) {
                String activeFunc = stack.peek();
                result.put(activeFunc, result.getOrDefault(activeFunc, 0) + (time - prevTime));
            }

            if (!stack.isEmpty() && stack.peek().equals(func)) {
                stack.pop(); // End the current function
            } else {
                stack.push(func); // Start a new function
            }

            prevTime = time;
        }

        return result;
    }
}


