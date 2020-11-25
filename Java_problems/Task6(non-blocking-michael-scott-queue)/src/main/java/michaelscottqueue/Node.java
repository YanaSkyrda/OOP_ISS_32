package michaelscottqueue;

import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    T data = null;
    AtomicReference<Node> next = new AtomicReference<>();

    Node(T data) {
        this.data = data;
    }

    Node() { }
}
