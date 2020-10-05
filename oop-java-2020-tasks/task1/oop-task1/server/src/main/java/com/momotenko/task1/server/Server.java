package com.momotenko.task1.server;

import com.momotenko.task1.server.entity.Delivery;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.lang.module.ModuleDescriptor.read;

public class Server {
    public static void main(String[] argc) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 4040));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);

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
    }

    private static void answer(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteArrayInputStream byteArrayInputStream;
        ObjectInput objectInput = null;
        Delivery delivery = null;
        client.read(buffer);
        try{
            byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            objectInput = new ObjectInputStream(byteArrayInputStream);
            delivery = (Delivery) objectInput.readObject();
            System.out.println("Received: ");
            System.out.println(delivery.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        buffer.clear();
        buffer = ByteBuffer.wrap("Received successfully".getBytes());

        //buffer.flip();
        client.write(buffer);
        buffer.clear();
    }

    private static void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        System.out.println("Client connected");
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public static Process start() throws IOException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classPath = System.getProperty("java.class.path");
        String className = Server.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin,"-cp",classPath,className);

        return builder.start();
    }
}
