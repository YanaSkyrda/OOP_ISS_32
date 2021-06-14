package problem5_disruptor;

public class Disruptor<E> {
    public static final int CAPACITY = 64;

    private final int capacity;
    private final E[] data;
    private volatile int readSequence;
    private volatile int writeSequence;

    public Disruptor(int capacity) {
        this.capacity = (capacity < 1) ? CAPACITY : capacity;
        this.data = (E[]) new Object[this.capacity];
        this.readSequence = 0;
        this.writeSequence = -1;
    }

    public boolean offer(E element) {
        boolean isFull = (writeSequence - readSequence) + 1 == capacity;
        if (!isFull) {
            int nextWriteSeq = writeSequence + 1;
            data[nextWriteSeq % capacity] = element;
            ++writeSequence;
            return true;
        }
        return false;
    }

    public E poll() {
        boolean isEmpty = writeSequence < readSequence;
        if (!isEmpty) {
            E nextValue = data[readSequence % capacity];
            ++readSequence;
            return nextValue;
        }
        return null;
    }
}
