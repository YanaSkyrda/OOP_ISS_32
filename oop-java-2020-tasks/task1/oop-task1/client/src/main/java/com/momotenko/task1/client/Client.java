package com.momotenko.task1.client;

import com.momotenko.task1.api.entity.Delivery;
import com.momotenko.task1.client.controller.ClientController;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] argc) throws IOException {
        ClientController clientController = new ClientController("localhost", 4040);
        clientController.run();
    }

    private static SocketChannel clientSocketChannel;
    private static ByteBuffer buffer;

    public Client(String hostname, int port) {
        try {
            clientSocketChannel = SocketChannel.open(new InetSocketAddress(hostname, port));
            buffer = ByteBuffer.allocate(1024);
            System.out.println("Connected to the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            clientSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = null;
    }

    public String sendMessage(Delivery delivery) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteBuffer inputBuffer = ByteBuffer.allocate(256);
        ObjectOutputStream outputStream;
        String response = null;

        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(delivery);
            outputStream.flush();

            buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

            clientSocketChannel.write(buffer);
            buffer.clear();
            clientSocketChannel.read(inputBuffer);

            System.out.println("Server response: ");
            response = new String(inputBuffer.array()).trim();
            System.out.println(response);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {

            }
        }

        return response;
    }
}
