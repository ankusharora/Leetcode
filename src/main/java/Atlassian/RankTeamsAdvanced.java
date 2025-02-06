package Atlassian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankTeamsAdvanced {
    public String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) return "";

        int numTeams = votes[0].length();

        // Map to store the total votes for each team
        Map<Character, Integer> totalVotes = new HashMap<>();

        // Map to store the votes at each position for each team
        Map<Character, int[]> positionVotes = new HashMap<>();

        // Map to store the earliest time when a team reached its final total votes
        Map<Character, Integer> winningTime = new HashMap<>();

        // Initialize the maps
        for (String vote : votes) {
            for (int i = 0; i < numTeams; i++) {
                char team = vote.charAt(i);
                totalVotes.put(team, totalVotes.getOrDefault(team, 0) + (numTeams - i));
                positionVotes.computeIfAbsent(team, k -> new int[numTeams])[i]++;
            }
        }

        // Calculate the winning time for each team
        for (int i = 0; i < votes.length; i++) {
            for (int j = 0; j < numTeams; j++) {
                char team = votes[i].charAt(j);
                if (!winningTime.containsKey(team)) {
                    winningTime.put(team, i);
                }
            }
        }

        // Create a list of teams
        List<Character> teams = new ArrayList<>(totalVotes.keySet());

        // Sort the teams using the custom comparator
        teams.sort((a, b) -> {
            // Compare total votes
            int totalVotesCompare = totalVotes.get(b).compareTo(totalVotes.get(a));
            if (totalVotesCompare != 0) return totalVotesCompare;

            // Compare winning time (first strategy)
            int timeCompare = winningTime.get(a).compareTo(winningTime.get(b));
            if (timeCompare != 0) return timeCompare;

            // Compare votes at each position (second strategy)
            int[] votesA = positionVotes.get(a);
            int[] votesB = positionVotes.get(b);
            for (int i = 0; i < numTeams; i++) {
                if (votesA[i] != votesB[i]) {
                    return votesB[i] - votesA[i];
                }
            }
            return 0;
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
        String[] votes1 = {"WXYZ","XYZW"};
        System.out.println(solution.rankTeams(votes)); // Output: "ACB"
        System.out.println(solution.rankTeams(votes1));
    }
}
