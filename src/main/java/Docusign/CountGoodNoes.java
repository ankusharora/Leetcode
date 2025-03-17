package Docusign;

public class CountGoodNoes {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    int goodNodes = 0;

    public int goodNodes(TreeNode root) {

        return dfs(root, root.val);
    }

    public int dfs(TreeNode node, int max) {
        if (node == null) {
            return 0;
        }
        int current = 0;
        if (node.val >= max) {
            current++;
        }
        int currentMax = Math.max(max, node.val);
        int left = dfs(node.left, currentMax);
        int right = dfs(node.right, currentMax);
        return current + left + right;
    }
}
