//package meta;
//
//public class BSTtoDoublyLinkedList {
//
//    Node head = null;
//    Node pre = null;
//
//    public Node treeToDoublyList(Node root) {
//
//        inOrderTraversel(root);
//        if (head == null) {
//            return null;
//        }
//        head.left = pre;
//        pre.right = head;
//        return head;
//    }
//
//    private void inOrderTraversel(Node root) {
//        if (root == null)
//            return;
//
//        inOrderTraversel(root.left);
//        if (head == null) {
//            head = root;
//        } else {
//            pre.right = root;
//            root.left = pre;
//        }
//
//        pre = root;
//
//        inOrderTraversel(root.right);
//    }
//}
