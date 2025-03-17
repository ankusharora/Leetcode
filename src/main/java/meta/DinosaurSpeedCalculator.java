package meta;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DinosaurSpeedCalculator {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: java DinosaurSpeedCalculator <dataset1.csv> <dataset2.csv>");
            System.exit(1);
        }

        String dataset1Path = args[0];
        String dataset2Path = args[1];

        Map<String, Double> legLengthMap = new HashMap<>();
        Map<String, Double> strideLengthMap = new HashMap<>();
        Set<String> bipedalDinos = new HashSet<>();

        // Read dataset1.csv (NAME, LEG_LENGTH, DIET)
        try (BufferedReader br = new BufferedReader(new FileReader(dataset1Path))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                String name = parts[0].trim();
                double legLength = Double.parseDouble(parts[1].trim());
                legLengthMap.put(name, legLength);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + dataset1Path);
            System.exit(1);
        }

        // Read dataset2.csv (NAME, STRIDE_LENGTH, STANCE)
        try (BufferedReader br = new BufferedReader(new FileReader(dataset2Path))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                String name = parts[0].trim();
                double strideLength = Double.parseDouble(parts[1].trim());
                String stance = parts[2].trim();
                if ("bipedal".equalsIgnoreCase(stance)) {
                    strideLengthMap.put(name, strideLength);
                    bipedalDinos.add(name);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + dataset2Path);
            System.exit(1);
        }

        // Compute speed for bipedal dinosaurs
        List<Dinosaur> dinosaurs = new ArrayList<>();
        for (String name : bipedalDinos) {
            if (legLengthMap.containsKey(name) && strideLengthMap.containsKey(name)) {
                double legLength = legLengthMap.get(name);
                double strideLength = strideLengthMap.get(name);
                double speed = ((strideLength / legLength) - 1) * Math.sqrt(legLength * 9.8);
                dinosaurs.add(new Dinosaur(name, speed));
            }
        }

        // Sort by speed in descending order
        dinosaurs.sort((a, b) -> Double.compare(b.speed, a.speed));

        // Print only names of bipedal dinosaurs from fastest to slowest
        dinosaurs.forEach(dino -> System.out.println(dino.name));
    }

    static class Dinosaur {
        String name;
        double speed;

        Dinosaur(String name, double speed) {
            this.name = name;
            this.speed = speed;
        }
    }
}

