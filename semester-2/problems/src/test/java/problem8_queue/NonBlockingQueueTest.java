package problem8_queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NonBlockingQueueTest {
    NonBlockingQueue queue;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        queue = new NonBlockingQueue();
    }

    private void addInThread(int number) {
        new Thread(() -> {
            try {
                queue.enqueue(number);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }).start();
    }

    private void removeInThread() {
        new Thread(() -> queue.dequeue()).start();
    }

    @Test
    public void enqueueInDifferentThreads() throws InterruptedException {
        for (int i = 0; i <= 100; i++) {
            addInThread(i);
        }
        Thread.sleep(5000);
        for (int i = 0; i < 100; i++) {
            assertTrue(queue.dequeue());
        }
    }

    @Test
    public void addAndRemoveInDifferentThreads() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < 100; i++) {
            addInThread(i);
            if (i % 2 == 0 && i - 1 >= 0) {
                removeInThread();
            }
        }
        Thread.sleep(5000);
        for (int i = 0; i < 50; i++) {
            assertTrue(queue.dequeue());
        }
    }
}