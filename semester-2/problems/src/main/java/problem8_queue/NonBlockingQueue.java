package problem8_queue;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class NonBlockingQueue {
    static public class Node {
        public Integer value;
        public volatile Node next;
        protected long offset;
        protected Unsafe unsafe;

        Node(Integer value, Node next) throws NoSuchFieldException, SecurityException, IllegalAccessException {
            this.value = value;
            this.next = next;
            unsafe = getUnsafe();
            offset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
        }

        public String toString() {
            return Integer.toString(value);
        }
    }

    private static Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    private final Node dummy;
    private final Node head;
    private final Node tail;
    private final long offsetHead;
    private final long offsetTail;
    private final Unsafe unsafe;

    public NonBlockingQueue() throws NoSuchFieldException, SecurityException, IllegalAccessException {
        dummy = new Node(null, null);
        head = dummy;
        tail = dummy;
        unsafe = getUnsafe();
        offsetHead = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("head"));
        offsetTail = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("tail"));
    }

    public void enqueue(int value) throws NoSuchFieldException, SecurityException, IllegalAccessException {
        Node newNode = new Node(value, null);
        while (true) {
            Node currentTail = tail;
            Node tailNext = currentTail.next;
            if (tailNext != null) {
                unsafe.compareAndSwapObject(this, offsetTail, currentTail, tailNext);
            } else {
                if (currentTail.unsafe.compareAndSwapObject(currentTail, currentTail.offset, null, newNode)) {
                    unsafe.compareAndSwapObject(this, offsetTail, currentTail, newNode);
                    return;
                }
            }
        }
    }

    public boolean dequeue() {
        if (head == dummy) {
            unsafe.compareAndSwapObject(this, offsetHead, dummy, dummy.next);
        }
        while (true) {
            Node first = head;
            Node last = tail;
            Node next = first.next;
            if (first == last) {
                if (next == null) {
                    return false;
                }
                unsafe.compareAndSwapObject(this, offsetTail, last, next);
            } else if (unsafe.compareAndSwapObject(this, offsetHead, first, next)) {
                return true;
            }
        }
    }

    public String toString() {
        StringBuilder answer = new StringBuilder();
        Node tmp = head;
        while (tmp != null) {
            if (tmp.value != null) {
                answer.append(tmp.value);
                if (tmp.next != null) {
                    answer.append(' ');
                }
            }
            tmp = tmp.next;
        }
        return answer.toString();
    }
}