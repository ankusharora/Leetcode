package Atlassian;

import java.util.HashMap;
import java.util.Map;

public class MiddlewareRouter {
    private static class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        String value = null; // Holds function name if this node is an endpoint
    }

    private TrieNode root;

    public MiddlewareRouter() {
        root = new TrieNode();
    }

    // Add route with function result
    public void addRoute(String path, String result) {
        String[] parts = path.split("/");
        TrieNode node = root;

        for (String part : parts) {
            if (!node.children.containsKey(part)) {
                node.children.put(part, new TrieNode());
            }
            node = node.children.get(part);
        }
        node.value = result;
    }

    // Call route and return the function result
    public String callRoute(String path) {
        String[] parts = path.split("/");
        return findMatch(root, parts, 0);
    }

    // Recursive function to match paths, including wildcards
    private String findMatch(TrieNode node, String[] parts, int index) {
        if (index == parts.length) {
            return node.value;
        }

        String part = parts[index];

        // Exact match case
        if (node.children.containsKey(part)) {
            String result = findMatch(node.children.get(part), parts, index + 1);
            if (result != null) return result;
        }

        // Wildcard match case
        if (node.children.containsKey("*")) {
            String result = findMatch(node.children.get("*"), parts, index + 1);
            if (result != null) return result;
        }

        return null;
    }

    public static void main(String[] args) {
        MiddlewareRouter router = new MiddlewareRouter();

        router.addRoute("/foo", "foo");
        router.addRoute("/bar/*/baz", "bar");

        System.out.println(router.callRoute("/bar/a/baz")); // Output: bar

        router.addRoute("/foo/baz", "foo");
        router.addRoute("/foo/*", "bar");

        System.out.println(router.callRoute("/foo/baz")); // Output: foo
        System.out.println(router.callRoute("/foo/test")); // Output: bar
    }
}

