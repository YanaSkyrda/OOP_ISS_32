package problem7_scheduler;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler {
    private enum Type {
        delay, date, repeat
    }

    private static class Status implements Comparable<Status> {
        public Runnable task;
        public Type type;
        public Date scheduledRun;
        public int period;

        public Status(Runnable task, Type type, Date scheduledRun, int period) {
            super();
            this.task = task;
            this.type = type;
            this.scheduledRun = scheduledRun;
            this.period = period;
        }

        @Override
        public int compareTo(Status s) {
            return scheduledRun.compareTo(s.scheduledRun);
        }
    }

    private static final int THREADS = 2;
    private final PriorityBlockingQueue<Status> inQueue = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<Status> processed = new PriorityBlockingQueue<>();

    private static class Supervisor implements Runnable {
        private final PriorityBlockingQueue<Status> inQueue;
        private final PriorityBlockingQueue<Status> processed;
        private boolean stopped = false;

        public Supervisor(PriorityBlockingQueue<Status> inQueue, PriorityBlockingQueue<Status> processed) {
            super();
            this.inQueue = inQueue;
            this.processed = processed;
        }

        @Override
        public void run() {
            while (!stopped) {
                while (!processed.isEmpty()) {
                    try {
                        var entry = processed.take();
                        if (entry.type == Type.repeat) {
                            inQueue.put(new Status(entry.task,
                                    Type.repeat,
                                    new Date(entry.scheduledRun.getTime() + entry.period),
                                    entry.period));
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void stop() {
            stopped = true;
        }
    }

    private static class SchedulerRunner implements Runnable {
        private final PriorityBlockingQueue<Status> inQueue;
        private final PriorityBlockingQueue<Status> processed;
        private boolean stopped = false;

        private final ExecutorService agent;

        public SchedulerRunner(PriorityBlockingQueue<Status> inQueue, PriorityBlockingQueue<Status> processed) {
            super();
            this.inQueue = inQueue;
            this.processed = processed;
            agent = Executors.newFixedThreadPool(THREADS);
        }

        @Override
        public void run() {
            while (!stopped) {
                try {
                    var entry = inQueue.take();
                    if (System.currentTimeMillis() < entry.scheduledRun.getTime()) {
                        inQueue.put(entry);
                        continue;
                    }
                    if (!agent.isShutdown())
                        agent.execute(entry.task);
                    processed.put(entry);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            stopped = true;
            agent.shutdown();
        }
    }

    private Thread runnerThread;
    private Thread supervisorThread;
    private final SchedulerRunner runner = new SchedulerRunner(inQueue, processed);
    private final Supervisor supervisor = new Supervisor(inQueue, processed);

    public Scheduler() {
    }

    public void start() {
        runnerThread = new Thread(runner);
        runnerThread.start();
        supervisorThread = new Thread(supervisor);
        supervisorThread.start();
    }

    public void stop() {
        runner.stop();
        supervisor.stop();
        try {
            runnerThread.join();
            supervisorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void schedule(Runnable task, int delay) {
        inQueue.add(new Status(task,
                Type.delay,
                new Date(System.currentTimeMillis() + delay),
                delay));
    }

    public void schedule(Runnable task, Date date) {
        inQueue.add(new Status(task,
                Type.date,
                date,
                0));
    }

    public void scheduleRepeat(Runnable task, int period) {
        inQueue.add(new Status(task,
                Type.repeat,
                new Date(System.currentTimeMillis()),
                period));
    }
}
