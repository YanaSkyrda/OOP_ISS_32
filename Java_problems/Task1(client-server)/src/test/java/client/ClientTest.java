package client;

import catObject.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientTest {
    private Client sut;
    private Cat cat = new Cat("Momo", 5);
    private SocketChannel clientChannel = Mockito.mock(SocketChannel.class);

    @BeforeEach
    void initializeClient() throws IOException {
        sut = new Client(clientChannel, cat);
    }

    @Test
    void shouldSendCatObject() throws IOException, ClassNotFoundException {
        //Given
        //When
        sut.sendToServer();
        //Then
        sut.buffer.rewind();
        ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(sut.buffer.array()));
        assertEquals(cat, reader.readObject());
    }

    @Test
    void shouldGetSuccessfulResponse() throws IOException {
        //Given
        doAnswer((Answer<Object>) invocation -> {
            sut.buffer = ByteBuffer.wrap("Cat successfully received".getBytes());
            return null;
        }).when(clientChannel).read(sut.buffer);
        //When
        String response = sut.getServerResponse();
        //Then
        verify(clientChannel).read(ByteBuffer.allocate(1024));
        assertEquals(response, "Cat successfully received");
    }
}
