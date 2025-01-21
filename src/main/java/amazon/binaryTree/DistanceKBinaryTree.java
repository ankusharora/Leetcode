package amazon.binaryTree;

import amazon.TreeNode;

import java.util.*;

public class DistanceKBinaryTree {

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
