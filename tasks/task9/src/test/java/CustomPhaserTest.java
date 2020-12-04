import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomPhaserTest {
    CustomPhaser phaser;
    int phase = 0;
    @BeforeEach
    void init() {
        phaser = new CustomPhaser();
    }

    Thread newThread() {
        Thread thread = new Thread(() -> {
            phaser.register();
            phase = phaser.arrive();
        });
        thread.start();
        return thread;
    }

    Thread newThreadWithAwait() {
        Thread thread = new Thread(() -> {
            phaser.register();
            phase = phaser.arriveAndAwaitAdvance();
        });
        thread.start();
        return thread;
    }

    Thread deregisterThread() {
        Thread thread = new Thread(() -> {
            phaser.register();
            phase = phaser.arriveAndDeregister();
        });
        thread.start();
        return thread;
    }

    @Test
    void mustReachSecondPhaseWithAwait() throws InterruptedException {
        newThread();
        newThreadWithAwait();
        newThread();
        assertEquals(1, phase);
    }

    @Test
    void mustReachSecondPhase() {
        newThread();
        newThread();
        newThread();
        assertEquals(1, phase);
    }


    @Test
    void mustReachThirdPhaseWithDeregister() {
        phaser.register();
        phaser.arrive();
        deregisterThread();
        deregisterThread();
        phase = phaser.arrive();
        assertEquals(2, phase);
    }
}
