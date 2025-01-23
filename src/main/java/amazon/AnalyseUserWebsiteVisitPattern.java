package amazon;
import java.util.*;
public class AnalyseUserWebsiteVisitPattern {
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {

        int n = username.length;

        List<UserVisit> visits = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            visits.add(new UserVisit(username[i], timestamp[i], website[i]));
        }

        visits.sort(Comparator.comparingInt(a -> a.timestamp));

        Map<String, List<String>> userWebsites = new HashMap<>();

        for (UserVisit visit : visits) {
            userWebsites.computeIfAbsent(visit.username, k -> new ArrayList<>()).add(visit.website);
        }

        Map<List<String>, Integer> sequenceCount = new HashMap<>();
        for (List<String> websites : userWebsites.values()) {
            if (websites.size() < 3)
                continue;

            Set<List<String>> seen = new HashSet<>();

            for (int i = 0; i < websites.size() - 2; i++) {
                for (int j = i + 1; j < websites.size() - 1; j++) {
                    for (int k = j + 1; k < websites.size(); k++) {
                        List<String> sequence = Arrays.asList(websites.get(i), websites.get(j), websites.get(k));
                        if (seen.add(sequence)) {
                            sequenceCount.put(sequence, sequenceCount.getOrDefault(sequence, 0) + 1);
                        }
                    }
                }
            }
        }

        List<String> result = new ArrayList<>();
        int maxCount = 0;
        for (Map.Entry<List<String>, Integer> entry : sequenceCount.entrySet()) {
            List<String> sequence = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount || (count == maxCount && sequence.toString().compareTo(result.toString()) < 0)) {
                result = sequence;
                maxCount = count;
            }
        }

        return result;

    }
}

class UserVisit {

    String username;
    int timestamp;
    String website;

    UserVisit(String username, int timestamp, String website) {
        this.username = username;
        this.timestamp = timestamp;
        this.website = website;
    }
}
