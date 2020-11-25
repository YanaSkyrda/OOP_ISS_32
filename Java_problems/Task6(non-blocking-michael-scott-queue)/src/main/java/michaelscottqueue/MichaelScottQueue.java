package michaelscottqueue;

import java.util.concurrent.atomic.AtomicReference;

public class MichaelScottQueue<T> {
    private final AtomicReference<Node> head;
    private final AtomicReference<Node> tail;

    public MichaelScottQueue() {
        Node dummy = new Node();
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T data) {
        Node node = new Node(data);
        Node currTailNode;
        Node currNextNode;

        while (true) {
            currTailNode = this.tail.get();
            currNextNode = (Node) currTailNode.next.get();

            if (currTailNode == this.tail.get()) {
                if (currNextNode == null) {
                    if (currTailNode.next.compareAndSet(null, node)) {
                        break;
                    }
                } else {
                    this.tail.compareAndSet(currTailNode, currNextNode);
                }
            }
        }
        this.tail.compareAndSet(currTailNode, node);
    }

    public T dequeue() {
        Node currHeadNode;
        Node currTailNode;
        Node currNextNode;
        T data;

        while (true) {
            currHeadNode = this.head.get();
            currTailNode = this.tail.get();
            currNextNode = (Node) (currHeadNode.next).get();

            if (currHeadNode == head.get()) {
                if (currHeadNode == currTailNode) {
                    if (currNextNode == null) {
                        return null;
                    }
                    this.tail.compareAndSet(currTailNode, currNextNode);
                } else {
                    data = (T) currNextNode.data;
                    if (head.compareAndSet(currHeadNode, currNextNode)) {
                        break;
                    }
                }
            }
        }

        return data;
    }
}
