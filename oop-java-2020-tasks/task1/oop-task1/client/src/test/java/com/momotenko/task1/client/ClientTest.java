package com.momotenko.task1.client;

import com.momotenko.task1.api.entity.Delivery;
import com.momotenko.task1.server.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ClientTest {
    final private String hostname = "localhost";
    final private int port = 1488;

    private Delivery getObjectFromBuffer(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        return new Delivery((Delivery) object);
    }

    @Test
    @DisplayName("check if message is sent and the server received correct result")
    void checkMessageIsSentAndReceivedCorrectly() throws IOException, ClassNotFoundException {
        Delivery delivery = new Delivery("Kyiv","Vlad","Yura", BigDecimal.valueOf(435));

        ArgumentCaptor<ByteBuffer> byteBufferArgumentCaptor = ArgumentCaptor.forClass(ByteBuffer.class);

        Server serverSpy = Mockito.spy(new Server(hostname,port));
        serverSpy.run();

        Client client = new Client(hostname,port);
        client.sendMessage(delivery);

        verify(serverSpy).answer(byteBufferArgumentCaptor.capture());

        Delivery newDelivery = getObjectFromBuffer(byteBufferArgumentCaptor.getValue());

        assertTrue(delivery.equals(newDelivery));

        client.stop();
        serverSpy.stop();
    }

    @Test
    @DisplayName("check that stop called only once")
    void checkStopCalledOnce(){
        Client client = mock(Client.class);
        client.stop();
        verify(client).stop();
    }
}
