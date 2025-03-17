package amazon.binaryTree;

public class DeleteNodeInBST {

    public TreeNode deleteNode(TreeNode root, int key) {
        // if()
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        }
        // (root.val == key)
        else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                // Node has two children
                TreeNode successor = findMin(root.right);
                root.val = successor.val; // Replace value with successor
                root.right = deleteNode(root.right, successor.val); // Delete successor
            }
        }
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
