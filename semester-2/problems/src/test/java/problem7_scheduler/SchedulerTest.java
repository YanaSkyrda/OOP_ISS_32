package problem7_scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SchedulerTest {
    Scheduler scheduler = new Scheduler();
    int num;

    @BeforeEach
    private void resetNum() {
        num = 0;
    }

    @Test
    public void testSchedulerWithDelay() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            scheduler.schedule(() -> ++num, 1);
        }
        scheduler.start();
        Thread.sleep(500);
        assertEquals(100, num);
    }

    @Test
    public void testSchedulerWithRepeat() throws InterruptedException {
        scheduler.scheduleRepeat(() -> ++num, 10);
        scheduler.start();
        Thread.sleep(5000);
        scheduler.stop();
        assertEquals(10, num, 1);
    }

}
