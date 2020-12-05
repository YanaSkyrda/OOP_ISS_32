

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

        threads.add(insertionThread(0));
        threads.add(insertionThread(100));
        threads.add(insertionThread(200));


        for (Thread thread: threads) {
            thread.join();
        }

       for (int i = 0; i < 300; i++) {
           assertTrue(list.contains(i));
       }
    }

    @Test
    void removingInCycleTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        threads.add(insertionThread(0));
        threads.add(insertionThread(100));
        threads.add(insertionThread(200));

        threads.add(removingThread(0));
        threads.add(removingThread(100));
        threads.add(removingThread(200));



        for (Thread thread: threads) {
            thread.join();
        }
        for (int i = 0; i < 5; i++) {
            assertFalse(list.contains(i));
        }


    }
    Thread insertionThread(int n) {
        Thread thread = new Thread(() -> {
            for (int i = n; i < n + 100; i++) {
                list.add(i);
            }
        });
        thread.start();
        return thread;
    }

    Thread removingThread(int n) {
        Thread thread = new Thread(() -> {
            for (int i = n; i < n + 100; i++) {
                list.remove(i);
            }
        });
        thread.start();
        return thread;
    }
}