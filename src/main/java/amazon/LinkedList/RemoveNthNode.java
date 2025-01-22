package amazon.LinkedList;

/**
 * Use two pointers, fast and slow, initialized at a dummy node before the head.
 * Move the fast pointer n + 1 steps ahead to create a gap of n nodes between fast and slow.
 * Traverse the list with both pointers until fast reaches the end, keeping the gap constant.
 * When fast reaches the end, slow will be just before the node to be removed.
 * Update slow.next to skip the nth node from the end.
 * Return dummy.next as the new head of the list.
 */
public class RemoveNthNode {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;

        for (int i = 1; i <= n + 1; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;

        return dummy.next;
    }


    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
