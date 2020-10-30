
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;


import static org.junit.jupiter.api.Assertions.assertTrue;


public class ThreadGroupServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testThreadServiceMethods() throws InterruptedException {
        ThreadGroup threadGroup1 = new ThreadGroup("FIRST GROUP");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "SECOND GROUP");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "THIRD GROUP");
    //    CountDownLatch latch = new CountDownLatch(1);
        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "firstThread").start();


        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "secondThread").start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thirdThread").start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "fourthThread").start();

        ThreadGroupService threadService = new ThreadGroupService();

        Thread printThread = new Thread(() -> {

            threadService.printTreadsInfo(threadGroup1);

        });
        printThread.start();

        while (printThread.isAlive()) {
            Thread.sleep(1000);
        }
        assertTrue( outContent.toString().length() > 0);
    }
}
