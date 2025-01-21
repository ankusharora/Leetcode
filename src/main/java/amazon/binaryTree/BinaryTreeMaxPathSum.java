package amazon.binaryTree;

public class BinaryTreeMaxPathSum {
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    public int maxGain(TreeNode node) {

        if (node == null) {
            return 0;
        }

        int leftG = Math.max(maxGain(node.left), 0);
        int rightG = Math.max(maxGain(node.right), 0);

        int price = node.val + leftG + rightG;
        maxSum = Math.max(maxSum, price);

        return node.val + Math.max(leftG, rightG);
    }
}
