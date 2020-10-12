import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadGroupInfoPrinter {
    private ThreadGroup threadGroup;
    private ScheduledExecutorService executorService;

    public ThreadGroupInfoPrinter(ThreadGroup group) {
        this.threadGroup = group;
    }
    private TimerTask printInfo = new TimerTask() {
        @Override
        public void run() {
            if (threadGroup.activeCount() == 0) {
                executorService.shutdownNow();
            } else {
                printGroupLevelsRecursively(threadGroup, 0);
            }
        }
    };

    private void printActiveThreads(ThreadGroup group, int level) {
        Thread[] activeThreads = new Thread[group.activeCount()];
        group.enumerate(activeThreads);
        for (Thread thread : activeThreads) {
            if (thread.getThreadGroup().equals(group)) {
                for (int i = 0; i < level; i++) {
                    System.out.print("-");
                }
                System.out.println(thread.getName());
            }
        }
    }

    private void printActiveGroups(ThreadGroup group, int level) {
        ThreadGroup[] activeThreadGroups = new ThreadGroup[group.activeGroupCount()];
        group.enumerate(activeThreadGroups);
        for (ThreadGroup threadGroup : activeThreadGroups) {
            if (threadGroup.getParent().equals(group)) {
                printGroupLevelsRecursively(threadGroup, level);
            }
        }
    }

    private void printLevelSeparator(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
    }

    private void printGroupLevelsRecursively(ThreadGroup group, int level) {
        printLevelSeparator(level);
        if (level == 0) {
            System.out.print("Current info for group thread: ");
        }
        System.out.println(group.getName());
        level = level + 2;

        printActiveThreads(group, level);
        printActiveGroups(group, level);
    }

    public void printInfoPeriodically(long period) {
        Thread threadForPrint = new Thread(() -> {
            executorService = new ScheduledThreadPoolExecutor(0);
            executorService.scheduleAtFixedRate(printInfo, 0L, period, TimeUnit.MILLISECONDS);
        });
        threadForPrint.start();
    }

    public boolean infoRunning() {
        if (executorService == null) {
            return false;
        }
        return (!executorService.isShutdown());
    }
}
