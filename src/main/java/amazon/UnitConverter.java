package amazon;

import java.util.*;

public class UnitConverter {
    // Graph representation: adjacency list where each unit maps to its neighbors and rates
    private Map<String, List<Edge>> graph;

    // Constructor to initialize the graph
    public UnitConverter() {
        graph = new HashMap<>();
    }

    // Add a conversion rate to the graph
    public void addConversion(String from, String to, double rate) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());

        // Add both directions for bidirectional conversions
        graph.get(from).add(new Edge(to, rate));
        graph.get(to).add(new Edge(from, 1.0 / rate));
    }

    // Perform a conversion between two units
    public double convert(String from, String to, double value) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            throw new IllegalArgumentException("Conversion path not available between " + from + " and " + to);
        }

        // Use BFS to find the conversion path
        Queue<Pair> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new Pair(from, value));
        visited.add(from);

        while (!queue.isEmpty()) {
            Pair current = queue.poll();

            if (current.unit.equals(to)) {
                return current.value;
            }

            for (Edge edge : graph.get(current.unit)) {
                if (!visited.contains(edge.to)) {
                    visited.add(edge.to);
                    queue.add(new Pair(edge.to, current.value * edge.rate));
                }
            }
        }

        throw new IllegalArgumentException("No conversion path found between " + from + " and " + to);
    }

    // Main method for testing
    public static void main(String[] args) {
        UnitConverter converter = new UnitConverter();

        // Add conversions
        converter.addConversion("meters", "feet", 3.281);
        converter.addConversion("feet", "inches", 12);
        converter.addConversion("centimeters", "inches", 0.3937);
        converter.addConversion("meters", "centimeters", 100);

        // Test conversions
        System.out.println(converter.convert("meters", "inches", 2)); // 78.744
        System.out.println(converter.convert("centimeters", "inches", 100)); // 39.37
        System.out.println(converter.convert("meters", "centimeters", 1)); // 100
    }

    // Helper class to represent edges in the graph
    private static class Edge {
        String to;
        double rate;

        Edge(String to, double rate) {
            this.to = to;
            this.rate = rate;
        }
    }

    // Helper class to represent a pair of unit and its converted value
    private static class Pair {
        String unit;
        double value;

        Pair(String unit, double value) {
            this.unit = unit;
            this.value = value;
        }
    }
}

