package Docusign;

import java.util.*;

public class MinReorder {

    public int minReorder(int n, int[][] connections) {

        Map<Integer, List<List<Integer>>> adjMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adjMap.put(i, new ArrayList<>());
        }

        for (int[] connection : connections) {
            adjMap.get(connection[0]).add(Arrays.asList(connection[1], 1));
            adjMap.get(connection[1]).add(Arrays.asList(connection[0], 0));
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(0);
        visited.add(0);

        int changes = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (List<Integer> neighor : adjMap.getOrDefault(current, new ArrayList<>())) {
                int nextCity = neighor.get(0);
                int isForwardEgde = neighor.get(1);

                if (visited.add(nextCity)) { // Only process unvisited nodes
                    changes += isForwardEgde; // Count forward edges
                    queue.add(nextCity); // Add the city for further exploration
                }
            }
        }

        return changes;
    }
}
