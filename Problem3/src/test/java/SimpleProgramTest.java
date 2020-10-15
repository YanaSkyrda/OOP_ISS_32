import org.junit.Assert;
import org.junit.Test;

public class SimpleProgramTest {

    @Test
    public void simulateWork_TEST() throws InterruptedException {
        synchronized (this) {
            SimpleProgram program = new SimpleProgram();

            Runnable programRunnable;
            programRunnable = () -> {
                try {
                    program.simulateWork();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            Thread programThread = new Thread(programRunnable);
            programThread.start();

            program.setThreadFlag(false);

            wait(2100);

            Assert.assertFalse(programThread.isAlive());
        }
    }
}
