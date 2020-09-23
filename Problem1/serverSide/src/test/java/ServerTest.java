import model.Cat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

public class ServerTest {

    private ServerSide serverSide;
    private ClientSide clientSide;
    Runnable taskServer;
    Thread thread;

    @Before
    public void setUp() throws IOException {
         taskServer = () -> {
            try {
                serverSide = new ServerSide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        thread = new Thread(taskServer);
        thread.start();

        clientSide = new ClientSide();
    }

    @Test
    public void checkCat_TRUE() throws IOException, InterruptedException {
        Cat catGiven = new Cat("Tom", "Black", 10);
        Cat catExpected;

        taskServer = () -> {
            try {
                serverSide.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        thread = new Thread(taskServer);
        thread.start();

        clientSide.run(catGiven);

        catExpected = serverSide.getCat();

        Assert.assertTrue(catGiven.equals(catExpected));

    }

}
