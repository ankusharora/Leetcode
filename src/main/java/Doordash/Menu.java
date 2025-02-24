package Doordash;

import java.util.*;

class Node {
    String key;
    int value;
    boolean active;
    List<Node> children;

    Node(String key, int value, boolean active) {
        this.key = key;
        this.value = value;
        this.active = active;
        this.children = new ArrayList<>();
    }
}

class Menu {
    public int countChangedNodes(Node existing, Node updated) {
        Map<String, Node> existingMap = new HashMap<>();
        buildMap(existing, existingMap);
        return countChanges(updated, existingMap);
    }

    private void buildMap(Node node, Map<String, Node> map) {
        if (node == null) return;
        map.put(node.key, node);
        for (Node child : node.children) {
            buildMap(child, map);
        }
    }

    private int countChanges(Node updated, Map<String, Node> existingMap) {
        if (updated == null) return 0;
        int changes = 0;

        Node existing = existingMap.get(updated.key);
        if (existing == null) {
            return countAllNodes(updated); // Treat all new nodes as changed
        }

        if (existing.value != updated.value || existing.active != updated.active) {
            changes++;
        }

        Set<String> existingChildren = new HashSet<>();
        for (Node child : existing.children) {
            existingChildren.add(child.key);
        }

        for (Node child : updated.children) {
            changes += countChanges(child, existingMap);
            existingChildren.remove(child.key);
        }

        for (String key : existingChildren) {
            changes += countAllNodes(existingMap.get(key)); // Soft delete case
        }

        return changes;
    }

    private int countAllNodes(Node node) {
        if (node == null) return 0;
        int count = 1;
        for (Node child : node.children) {
            count += countAllNodes(child);
        }
        return count;
    }

    public static void main(String[] args) {
        Menu menu = new Menu();

        Node existing = new Node("a", 1, true);
        Node b = new Node("b", 2, true);
        Node c = new Node("c", 3, true);
        Node d = new Node("d", 4, true);
        Node e = new Node("e", 5, true);
        Node f = new Node("f", 6, true);

        existing.children.add(b);
        existing.children.add(c);
        b.children.add(d);
        b.children.add(e);
        c.children.add(f);

        Node updated = new Node("a", 1, true);
        Node updatedC = new Node("c", 3, false);
        Node updatedF = new Node("f", 66, true);

        updated.children.add(updatedC);
        updatedC.children.add(updatedF);

        System.out.println("Changed Nodes: " + menu.countChangedNodes(existing, updated)); // Expected Output: 5
    }
}
