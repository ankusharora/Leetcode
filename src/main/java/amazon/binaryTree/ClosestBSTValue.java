package amazon.binaryTree;

/**
 * Start at the root and initialize the closest value as the root's value.
 * Compare the target with the current node's value to decide the traversal direction:
 * Move left if the target is smaller than the current value.
 * Move right if the target is larger than the current value.
 * Update the closest value whenever the current node is closer to the target than the previously recorded closest value.
 * Stop traversal when you reach a null node, as no further values exist.
 * Return the closest value found during the traversal.
 */
public class ClosestBSTValue {
    public int closestValue(TreeNode root, double target) {
        int closest = root.val;
        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(closest - target)) {
                closest = root.val;
            }
            if (target < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return closest;
    }
}
