//package Docusign;
//
//public class DeepCopyList {
//
//    public Node copyRandomList(Node head) {
//        if (head == null)
//            return null;
//
//        // Step 1: Create new nodes and insert them after original nodes
//        Node curr = head;
//        while (curr != null) {
//            Node copy = new Node(curr.val);
//            copy.next = curr.next;
//            curr.next = copy;
//            curr = copy.next;
//        }
//
//        // Step 2: Assign random pointers for the copied nodes
//        curr = head;
//        while (curr != null) {
//            if (curr.random != null) {
//                curr.next.random = curr.random.next;
//            }
//            curr = curr.next.next; // Move to the next original node
//        }
//
//        // Step 3: Separate the copied list from the original list
//        Node dummy = new Node(0);
//        Node copyCurr = dummy;
//        curr = head;
//
//        while (curr != null) {
//            Node copy = curr.next;
//            copyCurr.next = copy;
//            copyCurr = copy;
//
//            curr.next = copy.next; // Restore the original list
//            curr = curr.next;
//        }
//
//        return dummy.next; // Return the head of the copied list
//    }
//}
