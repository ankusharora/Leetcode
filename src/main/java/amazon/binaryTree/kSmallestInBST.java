package amazon.binaryTree;
import java.util.*;
public class kSmallestInBST {

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> arr = new ArrayList<>();

        dfs(root, arr);

        return arr.get(k-1);
    }

    private void dfs(TreeNode root, List<Integer> arr){
        if (root == null) return ;

        dfs(root.left, arr);
        arr.add(root.val);
        dfs(root.right, arr);
    }

}
