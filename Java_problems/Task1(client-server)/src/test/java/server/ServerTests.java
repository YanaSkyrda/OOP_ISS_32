package server;
import catObject.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServerTests {
    private ServerSocketChannel serverSocket = Mockito.mock(ServerSocketChannel.class);

     private Server sut;
     private final Cat cat = new Cat("Limo", 6);
     private Set<SelectionKey> setSelectionKeys = new HashSet<>();

     private SelectionKey acceptableSelectionKey = Mockito.mock(SelectionKey.class);
     private SelectionKey readableSelectionKey = Mockito.mock(SelectionKey.class);

     ServerTests() throws IOException {
    }

    @BeforeEach
    void init() throws IOException {
        SocketChannel client = Mockito.mock(SocketChannel.class);
        when(serverSocket.accept()).thenReturn(client);
        this.sut = Mockito.spy(Server.class);
        when(acceptableSelectionKey.readyOps()).thenReturn(SelectionKey.OP_ACCEPT);
        when(readableSelectionKey.readyOps()).thenReturn(SelectionKey.OP_READ);
    }

    @Test
    void shouldAcceptClient() throws IOException {
         //Given
         setSelectionKeys.add(acceptableSelectionKey);
         doNothing().when(sut).acceptClient();
         //When
        sut.checkSelectionKeys(setSelectionKeys);
        //Then
        verify(sut).acceptClient();
    }

    @Test
    void shouldReceiveObject() {
        //Given
        setSelectionKeys.add(readableSelectionKey);
        doNothing().when(sut).receiveObject(readableSelectionKey);
        //When
        sut.checkSelectionKeys(setSelectionKeys);
        //Then
        verify(sut).receiveObject(readableSelectionKey);
    }

    @Test
    void shouldReadToBufferSerializedCatObject() throws IOException {
        //Given
        SocketChannel client = Mockito.mock(SocketChannel.class);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //When
        sut.readToBuffer(client);
        //Then
        verify(client).read(buffer);
    }

    @Test
    void shouldReadObject() throws IOException, ClassNotFoundException {
        //Given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream writer = new ObjectOutputStream(byteArrayOutputStream);
        writer.writeObject(cat);
        writer.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        //When
        Serializable receivedCat = sut.readObject(buffer);
        //Then
        assertEquals(receivedCat, cat);
    }

    @Test
    void shouldSendResponseAboutSuccessfulReceiving() throws IOException {
        //Given
        SocketChannel client = Mockito.mock(SocketChannel.class);
        //When
        sut.sendResponse(true, client);
        //Then
        verify(client).write(ByteBuffer.wrap("Cat successfully received".getBytes()));
    }

    @Test
    void shouldSendResponseAboutUnsuccessfulReceiving() throws IOException {
        //Given
        SocketChannel client = Mockito.mock(SocketChannel.class);
        //When
        sut.sendResponse(false, client);
        //Then
        verify(client).write(ByteBuffer.wrap("Sorry, something went wrong".getBytes()));
    }
}
