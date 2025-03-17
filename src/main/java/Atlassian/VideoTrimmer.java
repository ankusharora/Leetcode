package Atlassian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoTrimmer {

    public static List<int[]> calculateInverseRange(List<int[]> intervals, int duration) {
        // Sort intervals based on the start time
        Collections.sort(intervals, Comparator.comparingInt(a -> a[0]));

        // Merge overlapping intervals
        List<int[]> mergedIntervals = new ArrayList<>();
        for (int[] interval : intervals) {
            if (mergedIntervals.isEmpty() || mergedIntervals.get(mergedIntervals.size() - 1)[1] < interval[0]) {
                mergedIntervals.add(interval);
            } else {
                int[] lastInterval = mergedIntervals.get(mergedIntervals.size() - 1);
                lastInterval[1] = Math.max(lastInterval[1], interval[1]);
            }
        }

        // Calculate the inverse ranges
        List<int[]> inverseRanges = new ArrayList<>();
        int prevEnd = 0;

        for (int[] interval : mergedIntervals) {
            if (prevEnd < interval[0]) {
                inverseRanges.add(new int[]{prevEnd, interval[0]});
            }
            prevEnd = Math.max(prevEnd, interval[1]);
        }

        // Add the last segment if necessary
        if (prevEnd < duration) {
            inverseRanges.add(new int[]{prevEnd, duration});
        }

        return inverseRanges;
    }

    public static void main(String[] args) {
        // Test Example 1
        List<int[]> sampleInput1 = new ArrayList<>();
        sampleInput1.add(new int[]{5, 15});
        sampleInput1.add(new int[]{25, 30});
        int duration1 = 40;
        List<int[]> result1 = calculateInverseRange(sampleInput1, duration1);
        System.out.println("Test Example 1:");
        for (int[] range : result1) {
            System.out.println("[" + range[0] + ", " + range[1] + "]");
        }

        // Test Example 2
        List<int[]> sampleInput2 = new ArrayList<>();
        sampleInput2.add(new int[]{0, 2});
        sampleInput2.add(new int[]{5, 15});
        sampleInput2.add(new int[]{25, 30});
        int duration2 = 40;
        List<int[]> result2 = calculateInverseRange(sampleInput2, duration2);
        System.out.println("Test Example 2:");
        for (int[] range : result2) {
            System.out.println("[" + range[0] + ", " + range[1] + "]");
        }

        // Test Example 3
        List<int[]> sampleInput3 = new ArrayList<>();
        sampleInput3.add(new int[]{0, 10});
        sampleInput3.add(new int[]{1, 6});
        sampleInput3.add(new int[]{4, 5});
        sampleInput3.add(new int[]{2, 8});
        sampleInput3.add(new int[]{15, 20});
        sampleInput3.add(new int[]{25, 30});
        int duration3 = 40;
        List<int[]> result3 = calculateInverseRange(sampleInput3, duration3);
        System.out.println("Test Example 3:");
        for (int[] range : result3) {
            System.out.println("[" + range[0] + ", " + range[1] + "]");
        }

        // Test Example 4
        List<int[]> sampleInput4 = new ArrayList<>();
        sampleInput4.add(new int[]{10, 20});
        sampleInput4.add(new int[]{5, 15});
        sampleInput4.add(new int[]{35, 50});
        sampleInput4.add(new int[]{30, 40});
        int duration4 = 60;
        List<int[]> result4 = calculateInverseRange(sampleInput4, duration4);
        System.out.println("Test Example 4:");
        for (int[] range : result4) {
            System.out.println("[" + range[0] + ", " + range[1] + "]");
        }
    }
}
