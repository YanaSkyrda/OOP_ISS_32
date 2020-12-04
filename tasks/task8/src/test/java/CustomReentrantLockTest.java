import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomReentrantLockTest {
    static Logger log = Logger.getLogger(CustomReentrantLock.class.getName());
    @Test
    void testLock() {
        CustomReentrantLock customReentrantLock = new CustomReentrantLock();
        int n = 10000;

        Thread thread1 = new Thread(() -> {
            for(int i = 0 ; i < n; i++) {

                customReentrantLock.lock();

                Counter.counter++;

                customReentrantLock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            for(int i = 0 ; i < n; i++) {
                customReentrantLock.lock();

                Counter.counter--;

                customReentrantLock.unlock();
            }
        });
        Thread thread3 = new Thread(() -> {
            for(int i=0;i<n;i++) {
                customReentrantLock.lock();

                Counter.counter++;

                customReentrantLock.unlock();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        }
        catch (InterruptedException e) {
            log.info(e.getMessage());
        }

        assertEquals(10000, Counter.counter);
    }
}
