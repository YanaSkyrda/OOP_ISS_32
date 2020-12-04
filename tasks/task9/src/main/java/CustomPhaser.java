import java.util.HashSet;
import java.util.Set;

public class CustomPhaser {

    private volatile int parties;
    private volatile int partiesAwait;
    private volatile int phase;

    public CustomPhaser(int parties) {
        this.parties = parties;
        this.partiesAwait = parties;
        this.phase = 0;
    }

    public synchronized int register() {
        parties++;
        partiesAwait++;
        return phase;
    }

    public synchronized int arrive() {
        partiesAwait--;

        if (partiesAwait == 0) {
            partiesAwait = parties;
            this.notifyAll();
        }

        return phase;
    }

    public int arriveAndAwaitAdvance() {
        int currentPhase = this.phase;
        synchronized (this) {
            partiesAwait--;

            while (partiesAwait > 0 && partiesAwait != parties) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            partiesAwait = parties;
            phase = currentPhase + 1;
            this.notifyAll();

            return this.phase;
        }
    }

    public synchronized int arriveAndDeregister() {
        partiesAwait--;
        parties--;
        if (this.partiesAwait == 0) {
            this.partiesAwait = parties;
            this.notifyAll();
        }

        return this.phase;
    }

    int getPhase() {
        return phase;
    }
}
