package problem4_skip_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SkipListTest {
    SkipList skipList = new SkipList(5);

    private void removeInThread(int number) {
        new Thread(() -> skipList.remove(number)).start();
    }

    @Test
    public void addSkipList() {
        for (int i = 0; i < 100; i++) {
            skipList.add(i);
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(skipList.contains(i));
        }
    }

    @Test
    public void addAndRemoveInDifferentThreads() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            skipList.add(i);
            if (i % 2 == 0 && i - 1 >= 0) {
                removeInThread(i - 1);
            }
        }
        Thread.sleep(5000);
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0 || i == 99) {
                assertTrue(skipList.contains(i));
            } else {
                assertFalse(skipList.contains(i));
            }

        }
    }

}
