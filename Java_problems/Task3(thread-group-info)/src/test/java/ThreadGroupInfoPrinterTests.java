import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadGroupInfoPrinterTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    List<ThreadGroup> threadGroups;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    public void setExampleGroups() {
        this.threadGroups = new ArrayList<>(3);
        ThreadGroup threadGroup1 = new ThreadGroup("Grandparent");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "Parent");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "Child");
        threadGroups.add(threadGroup1);
        threadGroups.add(threadGroup2);
        threadGroups.add(threadGroup3);

        Thread thread = new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setName("firstThread");
        thread.start();

        thread = new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setName("secondThread");
        thread.start();

        thread = new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setName("thirdThread");
        thread.start();

        thread = new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setName("fourthThread");
        thread.start();
    }

    @Test
    public void testExampleWithGrandparentGroup() {
        ThreadGroupInfoPrinter printer1 = new ThreadGroupInfoPrinter(threadGroups.get(0));
        printer1.printInfoPeriodically(5000);
        long endTime = System.currentTimeMillis() + 16000;
        while (System.currentTimeMillis() < endTime);
        assertEquals("Current info for group thread: Grandparent\r\n" +
                "--firstThread\r\n" +
                "--Parent\r\n" +
                "----secondThread\r\n" +
                "----thirdThread\r\n" +
                "----Child\r\n" +
                "------fourthThread\r\n" +
                "Current info for group thread: Grandparent\r\n" +
                "--Parent\r\n" +
                "----Child\r\n" +
                "------fourthThread\r\n" +
                "Current info for group thread: Grandparent\r\n" +
                "--Parent\r\n" +
                "----Child\r\n" +
                "------fourthThread\r\n"
                , outContent.toString());
    }

    @Test
    public void testExampleWithParentGroup() {
        ThreadGroupInfoPrinter printer1 = new ThreadGroupInfoPrinter(threadGroups.get(1));
        printer1.printInfoPeriodically(5000);
        long endTime = System.currentTimeMillis() + 16000;
        while (System.currentTimeMillis() < endTime);
        assertEquals("Current info for group thread: Parent\r\n" +
                "--secondThread\r\n" +
                "--thirdThread\r\n" +
                "--Child\r\n" +
                "----fourthThread\r\n" +
                "Current info for group thread: Parent\r\n" +
                "--Child\r\n" +
                "----fourthThread\r\n" +
                "Current info for group thread: Parent\r\n" +
                "--Child\r\n" +
                "----fourthThread\r\n"
                , outContent.toString());
    }

    @Test
    public void testExampleWithChildGroup() {
        ThreadGroupInfoPrinter printer1 = new ThreadGroupInfoPrinter(threadGroups.get(2));
        printer1.printInfoPeriodically(5000);
        long endTime = System.currentTimeMillis() + 16000;
        while (System.currentTimeMillis() < endTime);
        assertEquals("Current info for group thread: Child\r\n" +
                "--fourthThread\r\n" +
                "Current info for group thread: Child\r\n" +
                "--fourthThread\r\n" +
                "Current info for group thread: Child\r\n" +
                "--fourthThread\r\n"
                , outContent.toString());
    }
}
