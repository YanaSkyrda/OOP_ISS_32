import java.util.concurrent.Phaser;
public class CustomPhaser {
    private int parties = 0;
    private Integer partiesAwait = 0;
    private int phase = 0;

    public CustomPhaser() {}
    public CustomPhaser(int parties) {
        this.parties = parties;
        this.partiesAwait = parties;
    }

    int register() {
        ++parties;
        ++partiesAwait;
        return phase;
    }

    int arrive() {
        --partiesAwait;
        int currPhase = phase;
        synchronized (this) {
            if (partiesAwait == 0) {
                notifyAll();
                partiesAwait = parties;
                phase = currPhase + 1;
            }
        }
        return phase;
    }

    int arriveAndAwaitAdvance() {
        --partiesAwait;
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

    int arriveAndDeregister() {
        --partiesAwait;
        --parties;
        int currPhase = phase;
        synchronized (this) {
            if (partiesAwait == 0) {
                notifyAll();
                phase = currPhase + 1;
                partiesAwait = parties;
                return -1;
            }
        }
        return phase + 1;
    }

    int getPhase() {
        return phase;
    }
}
