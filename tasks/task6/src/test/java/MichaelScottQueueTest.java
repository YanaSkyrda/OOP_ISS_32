
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.*;

class MichaelScottQueueTest {
    static Logger log = Logger.getLogger(MichaelScottQueue.class);
    private MichaelScottQueue<Integer> queue;

    private boolean valuePresent[] = new boolean[5000];

    @BeforeEach
    void init() {
        queue = new MichaelScottQueue<>();
    }

    @Test
    void enqueueDequeueValuesFromManyThreadsTest() {
        queue = new MichaelScottQueue<>();
        enqueueValuesFromManyThreads();
        dequeueValuesFromManyThreads();
        for (int i = 0; i < 5000; i++) {
            assertTrue(valuePresent[i]);
        }
        assertNull(queue.dequeue());
    }
    @Test
    void queueMustContainValuesFromSetTest() {
        CyclicBarrier barrier = new CyclicBarrier(3);
        Set<Integer> set = new HashSet<>();
        set.add(50);
        set.add(100);

        new Thread(() -> {
            queue.enqueue(50);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                log.error(e);
            }
        }).start();
        new Thread(() -> {
            queue.enqueue(100);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                log.error(e);
            }
        }).start();

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error(e);
        }
        assertTrue(set.contains(queue.dequeue()));
        assertTrue(set.contains(queue.dequeue()));
        assertFalse(set.contains(queue.dequeue()));
    }

    private void enqueueValuesFromManyThreads() {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            int leftBound = 500 * j;
            int rightBound = 500 * (j + 1);
            threads[j] = new Thread(() -> {
                for (int i = leftBound; i < rightBound; i++) {
                    queue.enqueue(i);
                }
            });
            threads[j].start();
        }
        try {
            for (int i = 0; i < 10; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

    private void dequeueValuesFromManyThreads() {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            int leftBound = 500 * j;
            int rightBound = 500 * (j + 1);
            threads[j] = new Thread(() -> {
                int current;
                for (int i = leftBound; i < rightBound; i++) {
                    current = queue.dequeue();
                    valuePresent[current] = true;
                }
            });
            threads[j].start();
        }
        try {
            for (int i = 0; i < 10; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

}
