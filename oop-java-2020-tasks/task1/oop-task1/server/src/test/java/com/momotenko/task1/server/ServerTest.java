package com.momotenko.task1.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ServerTest {
    @Test
    @DisplayName("Test test")
    void test(){
        assertTrue(1 == 1);
    }

    final private String hostname = "localhost";
    final private int port = 1488;

    @Test
    @DisplayName("check that stop called only once")
    void checkStopCalledOnce(){
        Server server = spy(new Server(hostname,port));
        server.run();
        server.stop();
        verify(server).stop();
    }
}
