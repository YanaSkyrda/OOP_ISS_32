
import java.util.concurrent.atomic.AtomicReference;

public class MichaelScottQueue<E>  {


    private final AtomicReference<Node> queueHead;
    private final AtomicReference<Node> queueTail;


    public MichaelScottQueue() {

        Node n = new Node();
        queueHead = new AtomicReference<>(n);
        queueTail = new AtomicReference<>(n);

    }

    public void enqueue(E data) {
        Node n = new Node(data);
        Node currentTail = null;
        Node next = null;

        while (true) {
            currentTail = queueTail.get();
            next = currentTail.next.get();

            if (currentTail == queueTail.get()) {
                if (next == null) {
                    if (currentTail.next.compareAndSet(next, n)) {
                        break;
                    }
                } else {
                    queueTail.compareAndSet(currentTail, next);
                }
            }
        }
        queueTail.compareAndSet(currentTail, n);

    }

    public E dequeue() {
        Node head = null;
        Node currentTail = null;
        Node next = null;
        E data = null;

        while (true) {
            head = queueHead.get();
            currentTail = queueTail.get();
            next = head.next.get();

            if (head == queueHead.get()) {
                if (head == currentTail) {
                    if (next == null) {
                        // Queue is empty
                        return null;
                    }
                    queueTail.compareAndSet(currentTail, next);
                } else {
                    data = next.data;

                    if (queueHead.compareAndSet(head, next)) {
                        break;
                    }
                }
            }
        }

        return data;
    }


    private class Node {
        E data;
        AtomicReference<Node> next;

        Node() {
            this(null);
        }

        Node(E data) {
            this.data = data;
            this.next = new AtomicReference<>();
        }
    }

}