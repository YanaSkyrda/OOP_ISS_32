package problem5_disruptor;

import org.junit.jupiter.api.Test;
import problem.SkipList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DisruptorTest {
    Disruptor<Integer> disruptor = new Disruptor<>(50);

    @Test
    public void offerDisruptor() {
        for (int i = 0; i < 100; i++) {
            disruptor.offer(i);
        }
        for (int i = 0; i < 50; i++) {
            Integer res = disruptor.poll();
            assertTrue(res >= 0 && res < 100);
        }
    }

    @Test
    public void offerAndPoll() {
        for (int i = 0; i < 100; i++) {
            disruptor.offer(i);
            if (i % 2 == 0 && i - 1 >= 0) {
                disruptor.poll();
            }
        }
        for (int i = 0; i < 50; i++) {
            Integer res = disruptor.poll();
            assertTrue(res >= 0 && res < 100);
        }
    }
    
}
