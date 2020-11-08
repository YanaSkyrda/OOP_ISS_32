import java.util.concurrent.BrokenBarrierException;

public class CustomCyclicBarrier {
    private int initialNumberOfThreads;
    private int numberOfThreadsAwait;
    private boolean broken;
    private Runnable cyclicBarrierEvent;
    private int resets = 0;
    public CustomCyclicBarrier(int numberOfThreads, Runnable cyclicBarrrierEvent) {
        initialNumberOfThreads =numberOfThreads;
        numberOfThreadsAwait =numberOfThreads;
        this.cyclicBarrierEvent=cyclicBarrrierEvent;
        broken = false;
    }

    public synchronized void await() throws InterruptedException, BrokenBarrierException {
        if (broken) {
            throw new BrokenBarrierException();
        }
        int currentNumberOfParties = --numberOfThreadsAwait;
        int currentResets = resets;

        while(currentNumberOfParties>0){
            try {
                wait();
            } catch (InterruptedException e){
                this.broken = true;
                throw e;
            }

            if (resets != currentResets) {
                return;
            }
        }

        reset();
        notifyAll();

        cyclicBarrierEvent.run();

    }
    public void reset(){
        numberOfThreadsAwait = initialNumberOfThreads;
        broken = false;
        resets++;
    }
    public boolean isBroken() {
        return this.broken;
    }

    public int getThreadsAmount() {
        return this.initialNumberOfThreads;
    }

    public int getNumberWaiting() {
        return this.numberOfThreadsAwait;
    }

}
