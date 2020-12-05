

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LockFreeSkipListTest {

    private LockFreeSkipList list = new LockFreeSkipList();

    @Test
    public void createLockFreeSkipList() {
        assertNotNull(list);
    }

    @Test
    public void addItemTest() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.contains(2));
        assertTrue(list.contains(1));
        assertTrue(list.contains(3));
        assertFalse(list.contains(4));
    }

    @Test
    public void removeItemTest() {
        list.add(1);
        assertTrue(list.contains(1));
        list.remove(1);
        assertFalse(list.contains(1));
    }

    @Test
    void addItemFromMultipleThreadsTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            threads.add(insertionThread(i));
        }
        for (Thread thread: threads) {
            thread.join();
        }

        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
    }

    @Test
    void removingInCycleTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            threads.add(insertionThread(i));
        }

        for (int i = 0; i < 5; i++) {
            threads.add(removingThread(i));
        }

        for (Thread thread: threads) {
            thread.join();
        }
        for (int i = 0; i < 5; i++) {
            assertFalse(list.contains(i));
        }


    }
    Thread insertionThread(int value) {
        Thread thread = new Thread(() -> {
            list.add(value);
        });
        thread.start();
        return thread;
    }

    Thread removingThread(int element) {
        Thread thread = new Thread(() -> {
            list.remove(element);
        });
        thread.start();
        return thread;
    }
}