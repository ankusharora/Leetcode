package Atlassian;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContentPopularityDLL {
    // Node for the doubly linked list
    private class Node {
        int count; // Frequency count
        Set<Integer> contentIds; // Set of contentIds with this frequency
        Node prev, next;

        Node(int count) {
            this.count = count;
            this.contentIds = new HashSet<>();
        }
    }

    // HashMap to map contentId to its Node
    private Map<Integer, Node> contentToNodeMap;

    // HashMap to map frequency count to its Node
    private Map<Integer, Node> countToNodeMap;

    // Head and tail of the doubly linked list
    private Node head, tail;

    public ContentPopularityDLL() {
        contentToNodeMap = new HashMap<>();
        countToNodeMap = new HashMap<>();
        head = new Node(-1); // Dummy head
        tail = new Node(-1); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    // Increase the popularity of the specified contentId by one
    public void increasePopularity(int contentId) {
        // If the contentId is not in the map, add it with count 0
        if (!contentToNodeMap.containsKey(contentId)) {
            addContent(contentId);
        }

        // Get the current node for the contentId
        Node currentNode = contentToNodeMap.get(contentId);
        int currentCount = currentNode.count;

        // Move the contentId to the next frequency bucket
        moveContent(contentId, currentNode, currentCount + 1);
    }

    // Decrease the popularity of the specified contentId by one
    public void decreasePopularity(int contentId) {
        // If the contentId is not in the map, do nothing
        if (!contentToNodeMap.containsKey(contentId)) {
            return;
        }

        // Get the current node for the contentId
        Node currentNode = contentToNodeMap.get(contentId);
        int currentCount = currentNode.count;

        // If the count is 1, remove the contentId
        if (currentCount == 1) {
            removeContent(contentId, currentNode);
        } else {
            // Move the contentId to the previous frequency bucket
            moveContent(contentId, currentNode, currentCount - 1);
        }
    }

    // Return the contentId with the highest popularity
    public int mostPopular() {
        Node highestNode = tail.prev;

        // Traverse back until we find a node with content
        while (highestNode != head && highestNode.contentIds.isEmpty()) {
            highestNode = highestNode.prev;
        }

        return (highestNode == head) ? -1 : highestNode.contentIds.iterator().next();
    }



    private void addContent(int contentId) {
        Node oneNode = countToNodeMap.get(1); // Start from count 1
        if (oneNode == null) {
            oneNode = new Node(1);
            insertNodeAfter(head, oneNode);
            countToNodeMap.put(1, oneNode);
        }
        oneNode.contentIds.add(contentId);
        contentToNodeMap.put(contentId, oneNode);
    }


    // Helper method to move a contentId to a new frequency bucket
    private void moveContent(int contentId, Node currentNode, int newCount) {
        currentNode.contentIds.remove(contentId);

        // Remove the current node if it's empty
        if (currentNode.contentIds.isEmpty()) {
            removeNode(currentNode);
            countToNodeMap.remove(currentNode.count);
        }

        // Find the correct node to insert after
        Node prevNode = currentNode.prev;
        Node newNode = countToNodeMap.get(newCount);

        if (newNode == null) {
            newNode = new Node(newCount);

            // Insert at the correct sorted position
            insertNodeAfter(prevNode, newNode);

            countToNodeMap.put(newCount, newNode);
        }

        newNode.contentIds.add(contentId);
        contentToNodeMap.put(contentId, newNode);
    }



    // Helper method to remove a contentId from its current node
    private void removeContent(int contentId, Node currentNode) {
        currentNode.contentIds.remove(contentId);
        if (currentNode.contentIds.isEmpty()) {
            removeNode(currentNode);
            countToNodeMap.remove(currentNode.count);
        }
        contentToNodeMap.remove(contentId);
    }

    // Helper method to insert a new node after a given node
    private void insertNodeAfter(Node prevNode, Node newNode) {
        newNode.next = prevNode.next;
        newNode.prev = prevNode;
        prevNode.next.prev = newNode;
        prevNode.next = newNode;
    }

    // Helper method to remove a node from the linked list
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        ContentPopularityDLL cp = new ContentPopularityDLL();

        cp.increasePopularity(1);
        cp.increasePopularity(1);
        cp.increasePopularity(2);
        cp.increasePopularity(3);
        cp.increasePopularity(3);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 1 or 3 (tie)

        cp.decreasePopularity(1);
        cp.decreasePopularity(1);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 3

        cp.decreasePopularity(3);
        cp.decreasePopularity(3);

        System.out.println("Most Popular: " + cp.mostPopular()); // Output: 2
    }
}