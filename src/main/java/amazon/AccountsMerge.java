package amazon;
import java.util.*;
/**
 * Treat each email as a node and group emails into connected components using Union-Find.
 * Map emails to account names for easy lookup.
 * Union emails in the same account to create groups of connected emails.
 * Find the root email for each group using path compression.
 * Collect all connected emails for each root, sort them, and prepend the account name.
 * Return the sorted list of merged accounts.
 */
public class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, String> emailToName = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        // Initialize Union-Find structures
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                parent.put(email, email); // Initially, each email is its own parent
            }
        }

        // Union emails in the same account
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            for (int i = 2; i < account.size(); i++) {
                union(parent, firstEmail, account.get(i));
            }
        }

        // Find connected components
        Map<String, List<String>> groups = new HashMap<>();
        for (String email : parent.keySet()) {
            String root = find(parent, email);
            groups.computeIfAbsent(root, x -> new ArrayList<>()).add(email);
        }

        // Prepare the result
        List<List<String>> result = new ArrayList<>();
        for (List<String> group : groups.values()) {
            Collections.sort(group); // Sort emails lexicographically
            group.add(0, emailToName.get(group.get(0))); // Add the name at the front
            result.add(group);
        }

        return result;
    }

    private void union(Map<String, String> parent, String email1, String email2) {
        String root1 = find(parent, email1);
        String root2 = find(parent, email2);
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }

    private String find(Map<String, String> parent, String email) {
        if (!parent.get(email).equals(email)) {
            parent.put(email, find(parent, parent.get(email))); // Path compression
        }
        return parent.get(email);
    }
}
