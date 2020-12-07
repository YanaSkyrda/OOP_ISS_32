import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomPhaserTest {
    public CustomPhaser phaser;

    @Test
    void tests() throws InterruptedException {
        phaser = new CustomPhaser();
        Thread t1 = createThread();
        Thread t2 = createThread();
        phaser.register();
        t1.start();
        t2.start();
        Thread.sleep(100);
        assertEquals(0, phaser.getPhase());
        phaser.arriveAndAwaitAdvance();

        assertEquals(1, phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        assertEquals(2, phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        assertNotEquals(4, phaser.getPhase());

        phaser.arriveAndDeregister();
        assertEquals(0, phaser.getRegisteredThreads().size());
    }

    Thread createThread() {
        return new Thread(() -> {
            phaser.register();
            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();
        });
    }

}
