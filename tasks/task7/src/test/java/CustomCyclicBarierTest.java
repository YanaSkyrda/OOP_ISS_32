import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomCyclicBarierTest {
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
    void shouldReachBarrierAndMakeFlagTrue() throws InterruptedException, BrokenBarrierException {

        startNewThread();
        startNewThread();
        startNewThread();
        cyclicBarrier.await();


        assertTrue(barrierReached);
    }

    @Test
    void shouldReturnThatOneThreadIsAwaiting() throws InterruptedException {

        startNewThread();
        startNewThread();
        Thread.sleep(1000);

        assertFalse(barrierReached);
        assertEquals(1, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void shouldReturnThatAllThreadsAwaitingAfterBarrierReached() throws InterruptedException {

        startNewThread();
        startNewThread();
        startNewThread();
        Thread.sleep(1000);

        assertTrue(barrierReached);
        assertEquals(3, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void shouldReturnActualThreadsNumberWhenBarrierNotReached() throws InterruptedException {
        startNewThread();
        Thread.sleep(1000);
        assertFalse(barrierReached);
        assertEquals(3, cyclicBarrier.getThreadsAmount());
    }

    @Test
    void shouldReturnActualThreadsNumberWhenBarrierReached() throws InterruptedException, BrokenBarrierException {

        startNewThread();
        startNewThread();

        cyclicBarrier.await();


        assertTrue(barrierReached);
        assertEquals(3, cyclicBarrier.getThreadsAmount());
    }

    @Test
    void shouldReturnThatNotBrokenWhenBarrierNotReached() throws InterruptedException {

        startNewThread();
        synchronized (this) {
            this.wait(1500);
        }

        assertFalse(barrierReached);
        assertFalse(cyclicBarrier.isBroken());
    }
    @Test
    void shouldReturnThatNotBrokenWhenBarrierReached() throws InterruptedException {

        startNewThread();
        startNewThread();
        startNewThread();
        Thread.sleep(1000);

        assertTrue(barrierReached);
        assertFalse(cyclicBarrier.isBroken());
    }

    @Test
    void shouldReturnThat3ThreadsAwaitingAfterReset() throws InterruptedException {

        startNewThread();
        startNewThread();
        Thread.sleep(1000);

        cyclicBarrier.reset();

        assertFalse(cyclicBarrier.isBroken());

    }
}
