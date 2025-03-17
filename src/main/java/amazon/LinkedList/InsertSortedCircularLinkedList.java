package amazon.LinkedList;

/**
 *Traverse the list to find the correct position for the new value.
 * Insert the value between two nodes where prev.val <= insertVal <= curr.val.
 * Handle edge cases where:
 * The list has only one node.
 * The new value is smaller than the minimum or larger than the maximum (insert at the "rotation point").
 * If no suitable position is found during traversal (e.g., all values are the same),
 * insert the new value at any point (e.g., after the starting node).
 * Return the modified list.
 */
public class InsertSortedCircularLinkedList {

    class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    public Node insert(Node head, int insertVal) {

        if (head == null) {
            Node newNode = new Node(insertVal);
            newNode.next = newNode;
            return newNode;
        }
        Node current = head;
        while (true) {
            if (current.val <= insertVal && insertVal <= current.next.val) {
                break;
            }

            if (current.val > current.next.val && (insertVal >= current.val || insertVal <= current.next.val)) {
                break;
            }

            current = current.next;
            if (current == head) {
                break;
            }
        }
        Node newNode = new Node(insertVal);
        newNode.next = current.next;
        current.next = newNode;
        return head;
    }
}
