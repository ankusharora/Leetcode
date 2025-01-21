package amazon;

// Java program to implement the queue data structure using
// linked list

// Node class representing a single node in the linked list
class QueueNode {
    int data;
    Node next;

    QueueNode(int new_data) {
        this.data = new_data;
        this.next = null;
    }
}

// Class to implement queue operations using a linked list
class Queue {

    // Pointer to the front and the rear of the linked list
    QueueNode front, rear;

    // Constructor to initialize the front and rear
    Queue() { front = rear = null; }

    // Function to check if the queue is empty
    boolean isEmpty() {

        // If the front and rear are null, then the queue is
        // empty, otherwise it's not
        return front == null && rear == null;
    }

    // Function to add an element to the queue
    void enqueue(int new_data) {

        // Create a new linked list node
        QueueNode new_node = new QueueNode(new_data);

        // If queue is empty, the new node is both the front
        // and rear
        if (rear == null) {
            front = rear = new_node;
            return;
        }

        // Add the new node at the end of the queue and
        // change rear
        rear.next = new_node;
        rear = new_node;
    }

    // Function to remove an element from the queue
    void dequeue() {

        // If queue is empty, return
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return;
        }

        // Store previous front and move front one node
        // ahead
        QueueNode temp = front;
        front = front.next;

        // If front becomes null, then change rear also
        // to null
        if (front == null) {
            rear = null;
        }
    }

    // Function to get the front element of the queue
    int getFront() {

        // Checking if the queue is empty
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return Integer.MIN_VALUE;
        }
        return front.data;
    }

    // Function to get the rear element of the queue
    int getRear() {

        // Checking if the queue is empty
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return Integer.MIN_VALUE;
        }
        return rear.data;
    }
}

// Driver code to test the queue implementation
public class Main {
    public static void main(String[] args) {
        Queue q = new Queue();

        // Enqueue elements into the queue
        q.enqueue(10);
        q.enqueue(20);

        System.out.println("Queue Front: " + q.getFront());
        System.out.println("Queue Rear: " + q.getRear());

        // Dequeue elements from the queue
        q.dequeue();
        q.dequeue();

        // Enqueue more elements into the queue
        q.enqueue(30);
        q.enqueue(40);
        q.enqueue(50);

        // Dequeue an element from the queue
        q.dequeue();

        System.out.println("Queue Front: " + q.getFront());
        System.out.println("Queue Rear: " + q.getRear());
    }
}
