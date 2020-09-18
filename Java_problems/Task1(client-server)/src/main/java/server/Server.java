package server;

import catObject.Cat;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server extends Thread implements Runnable {
    private static final int PORT = 4004;
    private int sessionsCount = 0;
    ServerSocketChannel serverSocket;
    Selector selector;

    public Server() {
    }

    public static int getPORT() {
        return PORT;
    }

    public int getSessionsCount() {
        return sessionsCount;
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", PORT));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            try {
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keysIter = selectionKeys.iterator();

                    while (keysIter.hasNext()) {
                        SelectionKey key = keysIter.next();

                        if (key.isAcceptable()) {
                            acceptClient();
                        }

                        if (key.isReadable()) {
                            receiveObject(key);
                        }

                        keysIter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClient() {
        try {
            SocketChannel clientSocket = serverSocket.accept();
            clientSocket.configureBlocking(false);
            clientSocket.register(selector, SelectionKey.OP_READ);

            System.out.println("New client connected to this server");
            ++sessionsCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveObject(SelectionKey key) {
        SocketChannel clientSocket = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            clientSocket.read(buffer);
            ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));

            if (writeObjectToFile((Cat)reader.readObject())) {
                //clientSocket.write(ByteBuffer.wrap("Cat successfully received".getBytes()));
                ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
                for (Byte item : "Cat successfully received.".getBytes()) {
                    responseBuffer.put(item);
                }
                responseBuffer.flip();
                String x = StandardCharsets.UTF_8.decode(responseBuffer).toString();
                String x1 = responseBuffer.toString();
                clientSocket.write(responseBuffer);
            } else {
                clientSocket.write(ByteBuffer.wrap("Sorry, something went wrong".getBytes()));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cat class not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("One client disconnected");
                --sessionsCount;
            } catch (IOException e) {
                System.out.println("Can't close client channel");
                e.printStackTrace();
            }
        }
    }

    private boolean writeObjectToFile(Cat cat) {
        try {
            FileOutputStream writer = new FileOutputStream("output.txt", true);
            writer.write(cat.toString().getBytes());
            writer.write('\n');
            writer.flush();
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File for output not found. Cat wasn't written to file.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
