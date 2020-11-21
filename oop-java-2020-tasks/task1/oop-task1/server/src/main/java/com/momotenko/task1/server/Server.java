package com.momotenko.task1.server;

import com.momotenko.task1.api.entity.Delivery;
import com.momotenko.task1.server.controller.ServerController;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;
    private static ByteBuffer buffer;
    private volatile SelectionKey key;
    private volatile boolean running = false;

    public static void main(String[] argc) throws IOException {
        ServerController controller = new ServerController("localhost", 4040);
        controller.run();
    }

    public Server(String hostname,int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(hostname, port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            buffer = ByteBuffer.allocate(1024);
            running = true;
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: logs
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(()->{
            try {
                while (running) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        key = iterator.next();

                        if (key.isAcceptable()) {
                            register(selector, serverSocketChannel);
                        }

                        if (key.isReadable()) {
                            answer(buffer);
                        }

                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    public void stop(){
        running = false;
    }

    public boolean answer(ByteBuffer buffer) throws IOException {
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

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.printf("Receiving failed");
            return false;
        }
    }

    protected void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        System.out.println("Client connected");
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
}
