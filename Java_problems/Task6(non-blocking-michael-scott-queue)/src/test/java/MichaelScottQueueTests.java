import michaelscottqueue.MichaelScottQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MichaelScottQueueTests {
    private MichaelScottQueue<Integer> queue;
    volatile int dequeueElement = -1;

    @BeforeEach
    void initializeQueue() {
        queue = new MichaelScottQueue<>();
    }

    private Thread enqueueFromDifferentThread(int element) {
        Thread thread = new Thread(() -> {
            queue.enqueue(element);
        });
        thread.start();
        return thread;
    }

    private Thread dequeueInDifferentThread() {
        Thread thread = new Thread(() -> {
            dequeueElement = queue.dequeue();
        });
        thread.start();
        return thread;
    }

    @Test
    void shouldEnqueueElementsFrom3Threads() throws InterruptedException {
        //Given
        //When
        List<Thread> threads = new ArrayList<>();
        threads.add(enqueueFromDifferentThread(3));
        threads.add(enqueueFromDifferentThread(5));
        threads.add(enqueueFromDifferentThread(7));
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        List<Integer> dequeueResults = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dequeueResults.add(queue.dequeue());
        }
        for (int i = 3; i < 8; i += 2) {
            assertTrue(dequeueResults.contains(i));
        }
    }

    @Test
    void shouldDequeueElementsFrom3Threads() throws InterruptedException {
        //Given
        List<Thread> threads = new ArrayList<>();
        threads.add(enqueueFromDifferentThread(3));
        threads.add(enqueueFromDifferentThread(5));
        threads.add(enqueueFromDifferentThread(7));
        for (Thread thread : threads) {
            thread.join();
        }
        //When
        threads.clear();
        threads.add(dequeueInDifferentThread());
        threads.add(dequeueInDifferentThread());
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        Integer dequeueResult = queue.dequeue();
        assertNull(queue.dequeue());
        assertTrue(dequeueResult == 3 || dequeueResult == 5 || dequeueResult == 7);
    }

    @Test
    void shouldCorrectlyDeleteAndRemoveElementsInSameTime() throws InterruptedException {
        //Given
        List<Thread> threads = new ArrayList<>();
        threads.add(enqueueFromDifferentThread(3));
        for (Thread thread : threads) {
            thread.join();
        }
        //When
        threads.clear();
        threads.add(enqueueFromDifferentThread(5));
        threads.add(dequeueInDifferentThread());
        threads.add(enqueueFromDifferentThread(7));
        threads.add(dequeueInDifferentThread());
        for (Thread thread : threads) {
            thread.join();
        }
        //Then
        Integer dequeueResult = queue.dequeue();
        assertNull(queue.dequeue());
        assertTrue(dequeueResult == 3 || dequeueResult == 5 || dequeueResult == 7);
    }
}
