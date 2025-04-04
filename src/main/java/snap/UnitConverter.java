package snap;

import java.util.*;

public class UnitConverter {

    // Graph to store conversion rates
    private final Map<String, List<Edge>> graph = new HashMap<>();

    static class Edge {
        String target;
        double rate;

        Edge(String target, double rate) {
            this.target = target;
            this.rate = rate;
        }
    }

    // Add a conversion rate to the graph
    public void addConversion(String from, String to, double rate) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, rate));
        graph.computeIfAbsent(to, k -> new ArrayList<>()).add(new Edge(from, 1 / rate)); // Add reverse conversion
    }

    // Find conversion rate using DFS
    public double findConversionRate(String from, String to) {
        System.out.printf("findConversionRate(%s, %s)%n", from,to);
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return -1; // Units not found
        }
        Set<String> visited = new HashSet<>();
        return dfs(from, to, 1, visited);
    }

    private double dfs(String current, String target, double value, Set<String> visited) {

        System.out.printf(">> dfs(%s, %s) %n", current, target);

        if (current.equals(target)) {
            return value;
        }

        visited.add(current);
        List<Edge> orDefault = graph.getOrDefault(current, new ArrayList<>());

        orDefault.forEach(edge -> System.out.println(current + " to " + edge.target + " - " + edge.rate));
        for (Edge edge : orDefault) {
            if (!visited.contains(edge.target)) {
                double result = dfs(edge.target, target, value * edge.rate, visited);
                System.out.printf("dfs(%s, %s) result - %s%n", edge.target, target, result);
                if (result != -1) {
                    return result;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        UnitConverter converter = new UnitConverter();
        converter.addConversion("foot", "inch", 12);
        converter.addConversion("foot", "yard", 0.3333333);
        converter.addConversion("inch", "cm", 2.54);
        converter.addConversion("yard", "meter", 0.9144);

        double result = converter.findConversionRate("foot", "inch");
        System.out.println("Conversion rate from foot to inch: " + result);
        for (int i = 0; i < 50; i++) System.out.print("- ");

        result= converter.findConversionRate("inch", "meter");
        System.out.println("Conversion rate from inch to meter: " + result);
        for (int i = 0; i < 100; i++) System.out.print("- ");

        result= converter.findConversionRate("meter", "inch");
        System.out.println("Conversion rate from meter to inch: " + result);
        for (int i = 0; i < 100; i++) System.out.print("- ");

        result = converter.findConversionRate("foot", "cm");
        System.out.println("Conversion rate from foot to cm: " + result);
        for (int i = 0; i < 100; i++) System.out.print("- ");
        result = converter.findConversionRate("foot", "meter");
        System.out.println("Conversion rate from foot to meter: " + result );
    }
}
