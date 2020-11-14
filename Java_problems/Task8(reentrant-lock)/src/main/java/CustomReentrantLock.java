import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomReentrantLock implements CustomLock {
    private int holdCount = 0;
    private long currHoldingThread;

    @Override
    public synchronized void lock() {
        if (holdCount == 0) {
            ++holdCount;
            currHoldingThread = Thread.currentThread().getId();
        } else if (currHoldingThread != Thread.currentThread().getId()) {
            while (currHoldingThread != Thread.currentThread().getId()) {
                try {
                    this.wait();
                    ++holdCount;
                    currHoldingThread = Thread.currentThread().getId();
                } catch (InterruptedException ignored) { }
            }
        } else {
            ++holdCount;
        }
    }

    @Override
    public synchronized void lockInterruptibly() throws InterruptedException {
        if (holdCount == 0) {
            ++holdCount;
            currHoldingThread = Thread.currentThread().getId();
        } else if (currHoldingThread != Thread.currentThread().getId()) {
            try {
                this.wait();
                ++holdCount;
                currHoldingThread = Thread.currentThread().getId();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            ++holdCount;
        }
    }

    @Override
    public synchronized void unlock() throws IllegalMonitorStateException{
        if (holdCount == 0) {
            throw new IllegalMonitorStateException();
        }
        --holdCount;
        if (holdCount == 0) {
            notify();
        }
    }

    @Override
    public synchronized boolean tryLock() {
        if (holdCount == 0) {
            lock();
            return true;
        }
        return false;
    }
}
