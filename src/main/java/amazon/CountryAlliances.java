package amazon;

import java.util.*;

public class CountryAlliances {
    private Map<String, String> parent; // Maps each country to its parent
    private Map<String, Integer> rank; // Tracks rank for Union by Rank
    private int groups; // Number of distinct groups

    // Constructor: Initialize the data structure
    public CountryAlliances(List<List<String>> alliances) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        groups = 0;

        // Initialize all countries and alliances
        for (List<String> pair : alliances) {
            String country1 = pair.get(0);
            String country2 = pair.get(1);

            // Ensure both countries are in the data structure
            addCountry(country1);
            addCountry(country2);

            // Union the two countries
            union(country1, country2);
        }
    }

    // Add a country to the data structure
    private void addCountry(String country) {
        if (!parent.containsKey(country)) {
            parent.put(country, country); // Each country is its own parent
            rank.put(country, 0); // Initialize rank
            groups++; // New country creates a new group
        }
    }

    // Find the root of a country with path compression
    private String find(String country) {
        if (!country.equals(parent.get(country))) {
            parent.put(country, find(parent.get(country))); // Path compression
        }
        return parent.get(country);
    }

    // Union two countries
    private void union(String country1, String country2) {
        String root1 = find(country1);
        String root2 = find(country2);

        if (!root1.equals(root2)) {
            // Union by rank
            if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
            groups--; // Reduce the number of groups
        }
    }

    // Check if two countries are allies
    public boolean areAllies(String country1, String country2) {
        if (!parent.containsKey(country1) || !parent.containsKey(country2)) {
            return false;
        }
        return find(country1).equals(find(country2));
    }

    // Add a new alliance
    public void addAlliance(String country1, String country2) {
        addCountry(country1);
        addCountry(country2);
        union(country1, country2);
    }

    // Return the number of distinct groups
    public int getNumberOfGroups() {
        return groups;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example initialization
        List<List<String>> alliances = Arrays.asList(
                Arrays.asList("A", "B"),
                Arrays.asList("B", "C"),
                Arrays.asList("D", "E")
        );

        CountryAlliances countryAlliances = new CountryAlliances(alliances);

        // Test cases
        System.out.println(countryAlliances.areAllies("A", "C")); // true
        System.out.println(countryAlliances.areAllies("A", "E")); // false

        countryAlliances.addAlliance("C", "D");
        System.out.println(countryAlliances.areAllies("A", "E")); // true

        System.out.println(countryAlliances.getNumberOfGroups()); // 1
    }
}

