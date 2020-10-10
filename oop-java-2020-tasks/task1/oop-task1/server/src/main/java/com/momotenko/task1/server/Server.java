package com.momotenko.task1.server;

import com.momotenko.task1.api.entity.Delivery;
import com.momotenko.task1.server.controller.ServerController;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;
    private static ByteBuffer buffer;
    private static Server instance;


    public static void main(String[] argc) throws IOException {
        ServerController controller = new ServerController();
        controller.run();
    }

    private Server() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 4040));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            buffer = ByteBuffer.allocate(1024);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocketChannel);
                    }

                    if (key.isReadable()) {
                        answer(buffer, key);
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void answer(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteArrayInputStream byteArrayInputStream;
        ObjectInputStream objectInput;
        Delivery delivery = null;
        client.read(buffer);
        try {
            byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            objectInput = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInput.readObject();
            delivery = new Delivery((Delivery) object);
            buffer.clear();

            buffer = ByteBuffer.wrap("Received successfully".getBytes());


            System.out.println("Received: ");
            System.out.println(delivery.toString());

            client.write(buffer);
            buffer.clear();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.printf("Receiving failed");

        }

    }

    private void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        System.out.println("Client connected");
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public static Server start() throws IOException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }
}
