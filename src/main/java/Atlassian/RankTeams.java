package Atlassian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankTeams {
    public String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) return "";

        int numTeams = votes[0].length();

        // Map to store the votes at each position for each team
        Map<Character, int[]> positionVotes = new HashMap<>();

        // Initialize the map
        for (String vote : votes) {
            for (int i = 0; i < numTeams; i++) {
                char team = vote.charAt(i);
                positionVotes.computeIfAbsent(team, k -> new int[numTeams])[i]++;
            }
        }

        // Create a list of teams
        List<Character> teams = new ArrayList<>(positionVotes.keySet());

        // Sort the teams using the custom comparator
        teams.sort((a, b) -> {
            int[] votesA = positionVotes.get(a);
            int[] votesB = positionVotes.get(b);

            // Compare votes at each position
            for (int i = 0; i < numTeams; i++) {
                if (votesA[i] != votesB[i]) {
                    return votesB[i] - votesA[i]; // Higher votes come first
                }
            }

            // If votes are the same at all positions, sort alphabetically
            return a - b;
        });

        // Build the result string
        StringBuilder result = new StringBuilder();
        for (char team : teams) {
            result.append(team);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        RankTeams solution = new RankTeams();
        String[] votes = {"ABC", "ACB", "ABC", "ACB", "ACB"};
        System.out.println(solution.rankTeams(votes)); // Output: "ACB"
    }
}
