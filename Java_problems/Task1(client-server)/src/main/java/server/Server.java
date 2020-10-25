package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server extends Thread implements Runnable {
    private static final int PORT = 4004;
    private int sessionsCount = 0;
    private ServerSocketChannel serverSocket;
    private Selector selector;

    Server() {}
    public Server(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        this.serverSocket = serverSocketChannel;
        this.selector = selector;
    }

    public static int getPORT() {
        return PORT;
    }

    public int getSessionsCount() {
        return sessionsCount;
    }

    void checkSelectionKeys(Set<SelectionKey> selectionKeys) {
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

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                checkSelectionKeys(selector.selectedKeys());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Trying to close server.");
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Can't close server.");
            }
        }

    }

    void acceptClient() {
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

    void sendResponse(boolean objectSuccessfullyWritten, SocketChannel client) {
        try {
            if (objectSuccessfullyWritten) {
                client.write(ByteBuffer.wrap("Cat successfully received".getBytes()));
            } else {
                client.write(ByteBuffer.wrap("Sorry, something went wrong".getBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't send response to client.");
        }
    }

    ByteBuffer readToBuffer(SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        return buffer;
    }

    Serializable readObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.rewind();
        ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));

        return (Serializable) reader.readObject();
    }

    private void closeClientConnection(SocketChannel client) {
        try {
            client.close();
            System.out.println("One client disconnected");
            --sessionsCount;
        } catch (IOException e) {
            System.out.println("Can't close client channel");
            e.printStackTrace();
        }
    }
    void receiveObject(SelectionKey key) {
        SocketChannel clientSocket = (SocketChannel) key.channel();
        Serializable objectFromClient;
        boolean objectSuccessfullyWritten = false;

        try {
            ByteBuffer bufferFromClient = readToBuffer(clientSocket);
            objectFromClient = readObject(bufferFromClient);
            objectSuccessfullyWritten = writeObjectToFile(objectFromClient);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Class for client's object not found.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't read object.");
        }

        sendResponse(objectSuccessfullyWritten, clientSocket);
        closeClientConnection(clientSocket);
    }

    private boolean writeObjectToFile(Serializable cat) {
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
