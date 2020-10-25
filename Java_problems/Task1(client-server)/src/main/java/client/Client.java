package client;

import catObject.Cat;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class Client extends Thread {
    private SocketChannel client;
    ByteBuffer buffer;
    private Cat catToThrow;

    Client() {}
    public Client(SocketChannel client, Cat cat) {
        this.buffer = ByteBuffer.allocate(1024);
        this.catToThrow = cat;
        this.client = client;
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

     String getServerResponse() throws IOException {
        buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        buffer.rewind();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    void sendToServer() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream writer = new ObjectOutputStream(byteArrayOutputStream);
        writer.writeObject(catToThrow);
        writer.close();
        buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        client.write(buffer);
    }

    private String throwTheCat() throws IOException {
         try {
             sendToServer();
         } catch (IOException e) {
             e.printStackTrace();
             System.out.println("Can't send cat object to server.");
             return "";
         }

        return getServerResponse();
    }
}
