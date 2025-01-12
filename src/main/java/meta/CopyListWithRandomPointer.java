//package meta;
//
//
//
//class Solution {
//    public Node copyRandomList(Node head) {
//        if (head == null) return null;
//
//        //step 1 copy the each Node
//        Node current = head;
//        while( current != null){
//            Node copy = new Node(current.val);
//            copy.next = current.next; // A' --> B
//            current.next = copy; //A --> A'
//            current = copy.next; //Traver to next node
//        }
//
//
//        //ste 2
//
//        current = head;
//        while(current != null){
//            if (current.random != null){
//                current.next.random = current.random.next;
//            }
//            current = current.next.next;
//        }
//
//        // step 3 seperate the origin and copied nodes into two copyRandomList
//        Node original = head;
//        Node copyHead = head.next;
//        Node copy = copyHead;
//
//        while(original != null){
//            original.next = original.next.next;
//            if (copy.next != null){
//                copy.next = copy.next.next;
//            }
//            original = original.next;
//            copy = copy.next;
//        }
//        return copyHead;
//    }
//}
