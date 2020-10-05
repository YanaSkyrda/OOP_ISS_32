package com.momotenko.task1.client;

import com.momotenko.task1.client.controller.ClientController;
import com.momotenko.task1.client.entity.Delivery;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] argc) throws IOException {
        ClientController clientController = new ClientController();
        clientController.run();
    }

    private static SocketChannel clientSocketChannel;
    private static ByteBuffer buffer;
    private static Client instance;

    private Client() {
        try {
            clientSocketChannel = SocketChannel.open(new InetSocketAddress("localhost", 4040));
            buffer = ByteBuffer.allocate(256);
            System.out.println("Connected to the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Client start() {
        if (instance == null) {
            instance = new Client();
        }

        return instance;
    }

    public static void stop() throws IOException {
        clientSocketChannel.close();
        buffer = null;
    }

    public String sendMessage(Delivery delivery) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream;
        ObjectOutputStream outputStream = null;
        String response = null;

        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(delivery);
            outputStream.flush();

            buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

            clientSocketChannel.write(buffer);
            buffer.clear();
            clientSocketChannel.read(buffer);
            //TODO: check response
            System.out.println("Server response: ");
            response = new String(buffer.array()).trim();
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
