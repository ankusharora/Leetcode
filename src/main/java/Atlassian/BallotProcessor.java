package Atlassian;

import java.util.*;

public class BallotProcessor {

    public static List<String> getResults(List<List<String>> ballots) {
        // Map to store the total points for each candidate
        Map<String, Integer> candidatePoints = new HashMap<>();

        // Process each ballot
        for (List<String> ballot : ballots) {
            for (int i = 0; i < ballot.size(); i++) {
                String candidate = ballot.get(i);
                int points = 3 - i; // 3 points for 1st, 2 for 2nd, 1 for 3rd
                candidatePoints.put(candidate, candidatePoints.getOrDefault(candidate, 0) + points);
            }
        }

        // Convert the map entries to a list and sort by points in descending order
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(candidatePoints.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Extract the candidate names in sorted order
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }

    //The candidate who reaches the winning points first is declared the winner in case of a tie
    public static List<String> getResultsStrategt1(List<List<String>> ballots) {
        Map<String, Integer> candidatePoints = new HashMap<>();
        Map<String, Integer> candidateFirstAchieved = new HashMap<>(); // Track when a candidate first achieves their points

        int ballotIndex = 0;
        for (List<String> ballot : ballots) {
            for (int i = 0; i < ballot.size(); i++) {
                String candidate = ballot.get(i);
                int points = 3 - i; // 3 points for 1st, 2 for 2nd, 1 for 3rd
                candidatePoints.put(candidate, candidatePoints.getOrDefault(candidate, 0) + points);

                // Track the earliest ballot index where the candidate achieved their points
                if (!candidateFirstAchieved.containsKey(candidate)) {
                    candidateFirstAchieved.put(candidate, ballotIndex);
                }
            }
            ballotIndex++;
        }

        // Convert the map entries to a list and sort by points (descending), then by earliest ballot index (ascending)
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(candidatePoints.entrySet());
        sortedEntries.sort((a, b) -> {
            int pointCompare = b.getValue().compareTo(a.getValue()); // Sort by points (descending)
            if (pointCompare != 0) {
                return pointCompare;
            } else {
                // If points are equal, sort by the earliest ballot index (ascending)
                return candidateFirstAchieved.get(a.getKey()).compareTo(candidateFirstAchieved.get(b.getKey()));
            }
        });

        // Extract the candidate names in sorted order
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }

    // In case of a tie, prioritize candidates based on their positions in the ballots (0th index > 1st index > 2nd index).
    public static List<String> getResultsStrategy2(List<List<String>> ballots) {
        Map<String, Integer> candidatePoints = new HashMap<>();
        Map<String, int[]> candidateVotePositions = new HashMap<>(); // Track votes at each position (0th, 1st, 2nd)

        for (List<String> ballot : ballots) {
            for (int i = 0; i < ballot.size(); i++) {
                String candidate = ballot.get(i);
                int points = 3 - i; // 3 points for 1st, 2 for 2nd, 1 for 3rd
                candidatePoints.put(candidate, candidatePoints.getOrDefault(candidate, 0) + points);

                // Track the number of votes at each position
                if (!candidateVotePositions.containsKey(candidate)) {
                    candidateVotePositions.put(candidate, new int[3]); // Initialize array for 0th, 1st, 2nd positions
                }
                candidateVotePositions.get(candidate)[i]++; // Increment vote count at position i
            }
        }

        // Convert the map entries to a list and sort by points (descending), then by vote positions
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(candidatePoints.entrySet());
        sortedEntries.sort((a, b) -> {
            int pointCompare = b.getValue().compareTo(a.getValue()); // Sort by points (descending)
            if (pointCompare != 0) {
                return pointCompare;
            } else {
                // If points are equal, sort by vote positions (0th index > 1st index > 2nd index)
                int[] aVotes = candidateVotePositions.get(a.getKey());
                int[] bVotes = candidateVotePositions.get(b.getKey());
                for (int i = 0; i < 3; i++) {
                    if (aVotes[i] != bVotes[i]) {
                        return Integer.compare(bVotes[i], aVotes[i]); // Sort by votes at position i (descending)
                    }
                }
                return 0;
            }
        });

        // Extract the candidate names in sorted order
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            result.add(entry.getKey());
        }

        return result;
    }

    public static void main(String[] args) {
        // Example usage
        List<List<String>> ballots = new ArrayList<>();
        ballots.add(Arrays.asList("Alice", "Bob", "Charlie"));
        ballots.add(Arrays.asList("Bob", "Alice"));
        ballots.add(Arrays.asList("Charlie", "Alice", "Bob"));

        List<String> results = getResults(ballots);
        System.out.println(results); // Output: [Alice, Bob, Charlie]
    }
}
