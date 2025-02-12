package LinkedIn;

import java.util.*;

class Node {
    int val;
    List<Node> children;

    Node(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }
}

class Solution {
    public List<List<Integer>> findLeaves(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        // DFS function to get height
        getHeight(root, result);

        return result;
    }

    private int getHeight(Node node, List<List<Integer>> result) {
        if (node == null) return -1; // Base case (shouldn't be needed)

        int maxHeight = -1;
        for (Node child : node.children) {
            maxHeight = Math.max(maxHeight, getHeight(child, result));
        }

        int height = maxHeight + 1;

        if (height == result.size()) {
            result.add(new ArrayList<>());
        }

        result.get(height).add(node.val);

        return height;
    }
}

