
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    //private volatile boolean isRunning = true;
    private volatile Thread thread = Thread.currentThread();
    public ThreadPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            new Thread(new TaskWorker()).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (!thread.isInterrupted()) {
            workQueue.offer(command);
        }
    }

    public void shutdown() {
        thread.interrupt();
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (!thread.isInterrupted()) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}
