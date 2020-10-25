package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;

public class CustomCyclicBarrier {
    private int threadsAmount;
    private int threadsLeft;
    private Runnable barrierEvent;
    private boolean isBroken;

    public CustomCyclicBarrier(int threadsAmount, Runnable barrierEvent) {
        this.threadsAmount = threadsAmount;
        this.threadsLeft = threadsAmount;
        this.barrierEvent = barrierEvent;
        this.isBroken = false;
    }

    public CustomCyclicBarrier(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        this.threadsLeft = threadsAmount;
        this.barrierEvent = null;
    }

    public synchronized void await()
            throws InterruptedException, BrokenBarrierException {
        if (this.isBroken) {
            throw new BrokenBarrierException();
        }

        --this.threadsLeft;

        if (this.threadsLeft > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                this.isBroken = true;
                throw e;
            }
        } else {
            this.threadsLeft = this.threadsAmount;
            notifyAll();
            if (this.barrierEvent != null) {
                this.barrierEvent.run();
            }
        }
    }

    public void reset() {
        this.threadsLeft = this.threadsAmount;
        this.isBroken = false;
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public int getThreadsAmount() {
        return this.threadsAmount;
    }

    public int getNumberWaiting() {
        return this.threadsLeft;
    }
}
