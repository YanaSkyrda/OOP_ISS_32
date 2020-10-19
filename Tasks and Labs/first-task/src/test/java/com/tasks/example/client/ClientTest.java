package com.tasks.example.client;
import com.tasks.example.entity.Student;
import com.tasks.example.server.Server;
import com.tasks.example.server.ServerTest;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class ClientTest {
    private static final Logger log = Logger.getLogger(ClientTest.class.getName());

    private Client testClient;

    @Before
    public void clientSetup() {
        testClient = new Client();
    }

    @Test(expected = java.net.ConnectException.class)
    public void checkNoConnection() throws IOException {
        Student student = new Student("Darina", 20, "KPI");
        testClient.run(student);
    }

    @Test
    public void testSerialization() throws IOException, InterruptedException {
        Student student = new Student("Darina", 20, "KPI");

        ExecutorService service = Executors.newFixedThreadPool(2);
        Client mockClient = spy(new Client());
        Future<?> server = service.submit(new Runnable() {
            @Override
            public void run() {
                new Server().run();
            }
        });

        Thread.sleep(500);

        Future<?> client = service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mockClient.run(student);
                } catch (IOException e) {
                    log.log(Level.SEVERE, "Exception: ", e);
                }
            }
        });

        verify(mockClient).run(student);
        verify(mockClient).serializeAndSend(student);

    }

}