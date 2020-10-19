package com.tasks.example.client;

import com.tasks.example.entity.Student;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static SocketChannel socketChannel;
    private static ByteBuffer buffer;

    public static void main(String[] args) {
        Student student = new Student("Sanya", 19, "Taras Shevchenko");
        try {
            new Client().run(student);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }


    public void run(Student student) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 4545));
        buffer = ByteBuffer.allocate(256);
        serializeAndSend(student);
    }

    protected void serializeAndSend(Student student){
        ByteBuffer inputBuffer = ByteBuffer.allocate(256);
        ObjectOutputStream objectOutputStream;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();

            buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            socketChannel.write(buffer);
            buffer.clear();
            socketChannel.read(inputBuffer);
            System.out.println("Server response: ");
            String response = new String(inputBuffer.array()).trim();
            System.out.println(response);
            buffer.clear();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}
