import java.util.HashSet;
import java.util.Set;

public class CustomPhaser {
    private int phase = 0;

    private Set<Thread> registeredThreads = new HashSet<>();
    private Set<Thread> arrivedThreads = new HashSet<>();

    public Set<Thread> getRegisteredThreads() {
        return registeredThreads;
    }

    public Set<Thread> getArrivedThreads() {
        return arrivedThreads;
    }

    public void register() {
        synchronized (this) {
            registeredThreads.add(Thread.currentThread());
        }
    }

    public void arriveAndAwaitAdvance() {
        synchronized (this) {
            int currentPhase = phase;
            if(!registeredThreads.contains(Thread.currentThread())) {
                return;
            }
            arrivedThreads.add(Thread.currentThread());
            while(registeredThreads.size() > arrivedThreads.size() && currentPhase == phase) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(currentPhase == phase) {
                arrivedThreads.clear();
                phase++;
                this.notifyAll();
            }
        }
    }

    public void arriveAndDeregister() {
        synchronized (this) {
            registeredThreads.remove(Thread.currentThread());
            this.notifyAll();
        }
    }
    public int getPhase() {
        return phase;
    }
}
