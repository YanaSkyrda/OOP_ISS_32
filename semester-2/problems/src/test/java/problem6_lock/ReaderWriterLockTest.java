package problem6_lock;

import org.junit.jupiter.api.Test;
import problem5_disruptor.Disruptor;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReaderWriterLockTest {
    ReaderWriterLock lock = new ReaderWriterLock();

    private void acquireAndReleaseReaderLockInThread() {
        new Thread(() -> {
            lock.lockReader();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            lock.unlockReader();
        }).start();
    }

    private void acquireAndReleaseWriterLockInThread() {
        new Thread(() -> {
            lock.lockWriter();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            lock.unlockWriter();
        }).start();
    }

    @Test
    public void testLocksInDifferentThreads() {
        for (int i = 0; i < 100; i++) {
            acquireAndReleaseReaderLockInThread();
            acquireAndReleaseWriterLockInThread();
        }
        lock.lockWriter();
        lock.unlockWriter();
    }

}
