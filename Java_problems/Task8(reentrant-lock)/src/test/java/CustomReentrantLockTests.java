import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomReentrantLockTests {
    CustomReentrantLock lock;
    @BeforeEach
    void init() {
        lock = new CustomReentrantLock();
    }

    Thread startNewThread() {
        Thread thread = new Thread(() -> {
            lock.lock();
            lock.lock();
            lock.unlock();
            lock.unlock();
        });
        thread.start();
        return thread;
    }

    Thread startNewThreadInterruptible() {
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return thread;
    }

    @Test
    public void shouldLockAndReleaseLockSameAmountOfTimes() throws InterruptedException {
        //Given
        Thread exampleThread = startNewThread();
        //When
        exampleThread.join();
        //Then
        assertTrue(lock.tryLock());
        lock.unlock();
    }

    @Test
    public void shouldReturnFalseWhenTryLockBecauseOfTwoLocksAndOneUnlock() {
        //Given
        //When
        lock.lock();
        lock.lock();
        lock.unlock();
        //Then
        assertFalse(lock.tryLock());
        lock.unlock();
        assertTrue(lock.tryLock());
    }

    @Test
    public void svhouldReturnFalseWhenTryLockBecauseOfTwoLocksAndOneUnlock() {
        //Given
        lock.lock();
        Thread exampleThread = startNewThreadInterruptible();
        //When
        exampleThread.interrupt();
        //Then
        assertTrue(exampleThread.isInterrupted());
    }
}
