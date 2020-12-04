import java.util.HashSet;
import java.util.Set;

public class CustomPhaser {

    private int parties = 0;
    private int partiesAwait = 0;

    public int getPhase() {
        return phase;
    }

    private int phase = 0;
    public CustomPhaser() {
        this(0);
    }
    public CustomPhaser(int parties) {
        this.parties = parties;
        this.partiesAwait = parties;
    }

    int register() {
        parties++;
        partiesAwait++;
        return phase;
    }

    int arrive() {
        int currentPhase = phase;
        synchronized (this) {
            partiesAwait--;
            if (partiesAwait == 0) {
                notifyAll();
                partiesAwait = parties;
                phase = currentPhase + 1;
            }
        }
        return phase;
    }
    int arriveAndDeregister() {
        partiesAwait--;
        parties--;
        int currentPhase = phase;
        synchronized (this) {
            if (partiesAwait == 0) {
                phase = currentPhase + 1;
                notifyAll();
                partiesAwait = parties;
                return -1;
            }
        }
        return phase + 1;
    }
    int arriveAndAwaitAdvance() {
        partiesAwait--;
        int currPhase = phase;
        synchronized (this) {
            while (partiesAwait > 0) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        partiesAwait = parties;
        phase = currPhase + 1;
        synchronized (this) {
            notifyAll();
        }
        return phase;
    }
}
