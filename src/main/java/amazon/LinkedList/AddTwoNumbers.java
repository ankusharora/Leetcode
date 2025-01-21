package amazon.LinkedList;
public class AddTwoNumbers {
    /**
     Use a dummy node to construct the result list.
     Add digits from both lists along with carry.
     Treat missing digits in shorter list as 0.
     Compute digit = total % 10 and carry = total / 10.
     Continue until lists are traversed and carry is 0.
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode result = dummy;
        dummy.next = result;

        while (l1 != null || l2 != null || carry != 0) {
            int total = carry;

            if (l1 != null) {
                total += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                total += l2.val;
                l2 = l2.next;
            }
            int digit = total % 10;
            carry = total / 10;

            ListNode node = new ListNode(digit);
            result.next = node;
            result = result.next;
        }

        return result.next;
    }
}
