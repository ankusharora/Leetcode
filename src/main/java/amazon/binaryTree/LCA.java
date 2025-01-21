package amazon.binaryTree;

public class LCA {

    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;

        while (current != null){

            if (p.val > current.val && q.val > current.val){
                current = current.right;
            } else if (p.val < current.val && q.val < current.val){
                current = current.left;
            } else {
                return current;
            }
        }

        return null;
    }

    public TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestorBinaryTree(root.left, p, q);
        TreeNode right = lowestCommonAncestorBinaryTree(root.right, p, q);

        if(left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return root;
        }
    }


    public Node lowestCommonAncestor(Node p, Node q) {
        Node p1 = p;
        Node q1 = q;
        while (p1 != q1) {
            p1 = p1 == null ? q : p1.parent;
            q1 = q1 == null ? p : q1.parent;
        }
        return q1;
    }

}
