package client;

import catObject.Cat;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class Client extends Thread {
    private SocketChannel client;
    private ByteBuffer buffer;
    private Cat catToThrow;

    public Client(int port, Cat cat) throws IOException {
        this.client = SocketChannel.open(new InetSocketAddress("localhost", port));
        this.buffer = ByteBuffer.allocate(1024);
        this.catToThrow = cat;
    }

    @Override
    public void run() {
        String response;
        try {
            response = throwTheCat();
            if (response.equals("")) {
                System.out.println("Operation dismissed. Cat wasn't thrown.");
            } else {
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't read server response.");
        }
    }

    private String getServerResponse() throws IOException {
        buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        buffer.rewind();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    private void sendToServer(Cat cat) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream writer = new ObjectOutputStream(byteArrayOutputStream);
        writer.writeObject(cat);
        writer.close();
        buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        client.write(buffer);
    }

    private String throwTheCat() throws IOException {
         try {
             sendToServer(catToThrow);
         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Can't send cat object to server.");
             return "";
         }

        return getServerResponse();
    }
}
