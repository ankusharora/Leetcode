package Doordash;


import java.util.*;

public class NearestCityFinder {
    public static List<String> closestStraightCity(String[] c, int[] x, int[] y, String[] q) {
        int n = c.length;
        Map<String, int[]> cityMap = new HashMap<>();
        Map<Integer, TreeMap<Integer, String>> xMap = new HashMap<>();
        Map<Integer, TreeMap<Integer, String>> yMap = new HashMap<>();

        // Populate maps
        for (int i = 0; i < n; i++) {
            cityMap.put(c[i], new int[]{x[i], y[i]});
            xMap.computeIfAbsent(x[i], k -> new TreeMap<>()).put(y[i], c[i]);
            yMap.computeIfAbsent(y[i], k -> new TreeMap<>()).put(x[i], c[i]);
        }

        List<String> result = new ArrayList<>();

        for (String query : q) {
            if (!cityMap.containsKey(query)) {
                result.add("NONE");
                continue;
            }

            int[] coords = cityMap.get(query);
            int qx = coords[0], qy = coords[1];

            String nearestCity = "NONE";
            int minDist = Integer.MAX_VALUE;

            // Search along the same x-coordinate
            TreeMap<Integer, String> xCities = xMap.get(qx);
            if (xCities != null) {
                for (Map.Entry<Integer, String> entry : xCities.entrySet()) {
                    if (!entry.getValue().equals(query)) {
                        int dist = Math.abs(entry.getKey() - qy);
                        if (dist < minDist || (dist == minDist && entry.getValue().compareTo(nearestCity) < 0)) {
                            minDist = dist;
                            nearestCity = entry.getValue();
                        }
                    }
                }
            }

            // Search along the same y-coordinate
            TreeMap<Integer, String> yCities = yMap.get(qy);
            if (yCities != null) {
                for (Map.Entry<Integer, String> entry : yCities.entrySet()) {
                    if (!entry.getValue().equals(query)) {
                        int dist = Math.abs(entry.getKey() - qx);
                        if (dist < minDist || (dist == minDist && entry.getValue().compareTo(nearestCity) < 0)) {
                            minDist = dist;
                            nearestCity = entry.getValue();
                        }
                    }
                }
            }

            result.add(nearestCity);
        }

        return result;
    }

    public static void main(String[] args) {
        String[] c = {"c1", "c2", "c3"};
        int[] x = {3, 2, 1};
        int[] y = {3, 2, 3};
        String[] q = {"c1", "c2", "c3"};

        System.out.println(closestStraightCity(c, x, y, q));
    }
}
