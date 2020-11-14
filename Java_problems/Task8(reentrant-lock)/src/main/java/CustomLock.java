import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public interface CustomLock {
    void lock();
    void lockInterruptibly() throws InterruptedException;
    void unlock();
    boolean tryLock();
}
