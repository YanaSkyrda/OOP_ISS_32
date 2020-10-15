import org.junit.Assert;
import org.junit.Test;

public class ThreadManagerTest {

    @Test
    public void printThreadGroupHierarchy_TEST() throws InterruptedException {
        synchronized (this) {
            ThreadManager threadManager = new ThreadManager();
            SimpleProgram program = new SimpleProgram();

            ThreadGroup group = program.getMainThreadGroup();
            Runnable threadManagerRunnable;
            threadManagerRunnable = () -> {
                    threadManager.printThreadGroupHierarchy(group);
            };

            Thread threadManagerThread = new Thread(threadManagerRunnable);
            threadManagerThread.start();

            threadManager.setThreadFlag(false);

            wait(3200);

            Assert.assertFalse(threadManagerThread.isAlive());
        }
    }
}
