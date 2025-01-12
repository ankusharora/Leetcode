package box;


class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }


public class CountUnivalueSubtrees {
    int count = 0;

    public boolean dfs(TreeNode node) {
        if (node == null) {
            return true;
        }

        boolean left = dfs(node.left);
        boolean right = dfs(node.right);


        if (left && right) {
            if (node.left != null && node.left.val != node.val) {
                return false;
            }
            if (node.right != null && node.right.val != node.val) {
                return false;
            }
            count++;
            return true;
        }
        return false;
    }

    public int countUnivalSubtrees(TreeNode root) {
        dfs(root);
        return count;
    }
}
