import cyclicbarrier.CustomCyclicBarrier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;

import static org.junit.jupiter.api.Assertions.*;

class CustomCyclicBarrierTests {
    CustomCyclicBarrier cyclicBarrier;
    boolean barrierReached = false;
    @BeforeEach
    void init() {
        cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached = true);
    }

    Thread startNewThread() {
        Thread thread = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return thread;
    }

    @Test
    void shouldReachBarrierAndMakeFlagTrue() throws InterruptedException {
        //Given
        //When
        startNewThread();
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertTrue(barrierReached);
    }

    @Test
    void shouldReturnThatOneThreadIsAwaiting() throws InterruptedException {
        //Given
        //When
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertFalse(barrierReached);
        assertEquals(1, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void shouldReturnThatAllThreadsAwaitingAfterBarrierReached() throws InterruptedException {
        //Given
        //When
        startNewThread();
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertTrue(barrierReached);
        assertEquals(3, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void shouldReturnActualThreadsNumberWhenBarrierNotReached() throws InterruptedException {
        //Given
        //When
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertFalse(barrierReached);
        assertEquals(3, cyclicBarrier.getThreadsAmount());
    }

    @Test
    void shouldReturnActualThreadsNumberWhenBarrierReached() throws InterruptedException {
        //Given
        //When
        startNewThread();
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertTrue(barrierReached);
        assertEquals(3, cyclicBarrier.getThreadsAmount());
    }

    @Test
    void shouldReturnThatNotBrokenWhenBarrierNotReached() throws InterruptedException {
        //Given
        //When
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertFalse(barrierReached);
        assertFalse(cyclicBarrier.isBroken());
    }

    @Test
    void shouldReturnThatNotBrokenWhenBarrierReached() throws InterruptedException {
        //Given
        //When
        startNewThread();
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //Then
        assertTrue(barrierReached);
        assertFalse(cyclicBarrier.isBroken());
    }

    @Test
    void shouldReturnThat3ThreadsAwaitingAfterReset() throws InterruptedException {
        //Given
        startNewThread();
        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }
        //When
        cyclicBarrier.reset();
        //Then
        assertFalse(cyclicBarrier.isBroken());
        assertEquals(3, cyclicBarrier.getNumberWaiting());
    }
}
