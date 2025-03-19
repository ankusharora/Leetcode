package meta;

import java.io.*;
import java.util.*;

public class DuplicateRecordsChecker {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Usage: java DuplicateRecordsChecker <file1> <file2> ... <fileN>");
            System.exit(1);
        }

        Map<String, Set<String>> recordToFilesMap = new HashMap<>();
        boolean hasDuplicates = false;

        // Read each file
        for (String filename : args) {
            File file = new File(filename);
            if (!file.exists()) {
                System.err.println("Error: File " + filename + " does not exist.");
                System.exit(1);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        recordToFilesMap.computeIfAbsent(line, k -> new LinkedHashSet<>()).add(filename);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + filename);
                System.exit(1);
            }
        }

        // Check for duplicates
        for (Map.Entry<String, Set<String>> entry : recordToFilesMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                hasDuplicates = true;
                System.out.println(formatOutput(entry.getKey(), entry.getValue()));
            }
        }

        // Exit with appropriate status
        System.exit(hasDuplicates ? 1 : 0);
    }

    private static String formatOutput(String record, Set<String> files) {
        return record + " is in " + String.join(" and ", files);
    }
}

