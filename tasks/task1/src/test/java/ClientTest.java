import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientTest {
    private Client client;
    private Device device = new Device("Nokia", 10, 15);
    private SocketChannel clientChannel = Mockito.mock(SocketChannel.class);

    @BeforeEach
    void init() {
        client = new Client(clientChannel, device);
    }
    @Test
    void getResponseTest() throws IOException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                client.buffer = ByteBuffer.wrap("Device successfully received".getBytes());
                return null;
            }
        }).when(clientChannel).read(client.buffer);
        String response = client.getResponse();
        verify(clientChannel).read(ByteBuffer.allocate(1024));
        assertEquals(response, "Device successfully received");
    }
    @Test
    void sendDeviceObjectTest() throws IOException, ClassNotFoundException {
        client.sendToServer();
        client.buffer.rewind();
        ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(client.buffer.array()));
        Device deviceFromBuffer =  (Device) reader.readObject();
        assertEquals(deviceFromBuffer.name, device.name);
    }


}
