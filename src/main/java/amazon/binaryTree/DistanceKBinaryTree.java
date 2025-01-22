package amazon.binaryTree;

import java.util.*;

/**
 *
 * Build Parent Map: Create a map to store the parent of each node for upward traversal during the search.
 * Depth-First Search (DFS): Start DFS from the target node to find nodes at distance k.
 * Traverse All Directions: Explore left, right, and the parent node to account for all possible paths.
 * Track Visited Nodes: Use a visited set to prevent revisiting nodes and infinite loops.
 * Add to Result: If k == 0, add the current node's value to the result list.
 *
 */
public class DistanceKBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    List<Integer> returnList = new ArrayList<>();
    Set<TreeNode> visited = new HashSet<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        buildMap(root, null);

        dfs(target, k, visited);

        return returnList;

    }

    public void buildMap(TreeNode node, TreeNode parent) {
        if (node == null)
            return;
        parentMap.put(node, parent);
        buildMap(node.left, node);
        buildMap(node.right, node);
    }

    public void dfs(TreeNode node, int k, Set<TreeNode> visited) {
        if (node == null || visited.contains(node))
            return;

        visited.add(node);

        if (k == 0) {
            returnList.add(node.val);
            return;
        }
        dfs(node.left, k - 1, visited);
        dfs(node.right, k - 1, visited);
        dfs(parentMap.get(node), k - 1, visited);
    }

}
