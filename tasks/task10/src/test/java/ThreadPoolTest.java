
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadPoolTest {
    private final Counter counter = new Counter();
    @Test
    public void checkSpeedUp() throws Exception {

        long timeWithMultipleCores = timeWithThreads(9);
        long timeWithOneCore = timeWithThreads(1);
 
        assertTrue(timeWithMultipleCores < timeWithOneCore );
    }

    private long timeWithThreads(int threads) throws Exception {
        ThreadPool threadPool = new ThreadPool(threads);
        long start = System.nanoTime();
        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> counter.count(j),
                            threadPool
                    ));
        }
        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }
        return System.nanoTime() - start;
    }
}