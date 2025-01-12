package Docusign;

import java.util.HashMap;
import java.util.Map;

class Node {
    int key;
    int value;
    Node next;
    Node prev;
}

class LRUCache {

    Node head;
    Node tail;
    int capacity;
    Map<Integer, Node> map;

    public LRUCache(int capacity) {
        this.head = new Node();
        this.tail = new Node();
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public int get(int key) {
        int result = -1;
        Node node = map.get(key);
        if (node != null){
            result = node.value;
            remove(node);
            add(node);
        }
        return result;
    }

    public void remove (Node node){
        Node next = node.next;
        Node prev = node.prev;

        prev.next = next;
        next.prev = prev;

    }

    public void add (Node node){
        Node headNext = head.next;
        head.next = node;
        node.prev = head;

        node.next = headNext;
        headNext.prev = node;

    }
    public void put(int key, int value) {
        Node node = map.get(key);

        if (node != null){
            remove(node);
            node.value = value;
            add(node);
        } else {
            if (map.size() == capacity){
                map.remove(tail.prev.value);
                remove(tail.prev);
            }

            Node newNode = new Node();
            newNode.key = key;
            newNode.value = value;
            map.put(key, newNode);
            add(newNode);
        }

    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
