import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class CustomThreadPool implements Executor {
    private final Queue<Runnable> tasksQueue = new ConcurrentLinkedQueue<>();
    private int threadsAmount;
    private Thread[] workerThreads;
    private volatile boolean stillWorking = true;

    public CustomThreadPool(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        this.workerThreads = new Thread[threadsAmount];
        for (int i = 0; i < threadsAmount; i++) {
            this.workerThreads[i] = new Thread(new Worker());
            this.workerThreads[i].start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (stillWorking) {
            tasksQueue.offer(command);
        }
    }

    public void shutdown() {
        stillWorking = false;
        for (int i = 0; i < threadsAmount; i++) {
            workerThreads[i].interrupt();
        }
    }

    public boolean isStillWorking() {
        return stillWorking;
    }

    public final class Worker implements Runnable {
        @Override
        public void run() {
            while (stillWorking) {
                Runnable task = tasksQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
