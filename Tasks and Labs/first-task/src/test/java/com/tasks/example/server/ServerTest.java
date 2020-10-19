package com.tasks.example.server;


import com.tasks.example.client.Client;
import com.tasks.example.entity.Student;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class ServerTest {
    private static final Logger log = Logger.getLogger(ServerTest.class.getName());

    @Test
    public void verifySetUp() throws IOException {
        Server mockServer = spy(new Server());
        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<?> server = service.submit(new Runnable() {
            @Override
            public void run() {
                mockServer.run();
            }
        });
        service.shutdown();
        verify(mockServer).run();
        verify(mockServer).setUp();
    }

    @Test
    public void testRegisterAndRespond() throws InterruptedException, IOException {
        Server mockServer = spy(new Server());

        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<?> server = service.submit(new Runnable() {
            @Override
            public void run() {
                mockServer.run();
            }
        });

        Thread.sleep(500);

        Student student = new Student("Igor", 20, "KPI");

        Future<?> client = service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client().run(student);
                } catch (IOException e) {
                    log.log(Level.SEVERE, "Exception: ", e);
                }
            }
        });

        service.shutdown();

        verify(mockServer).run();
        verify(mockServer).setUp();
        verify(mockServer, atLeastOnce()).processKeys();
        verify(mockServer).register();
    }
}