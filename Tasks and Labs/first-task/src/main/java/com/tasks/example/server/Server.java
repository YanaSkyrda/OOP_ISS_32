package com.tasks.example.server;

import com.tasks.example.entity.Student;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static ByteBuffer buffer;
    private static Student student;

    public static void main(String[] args) {
        new Server().run();
    }

    protected void setUp() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 4545));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public Server(){
        student = new Student();
        buffer = ByteBuffer.allocate(1024);
    }

    public void run() {
        try{
            setUp();
            processKeys();
        } catch (IOException e){
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }

    protected void processKeys() throws IOException {
        while (true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    register();
                }

                if(selectionKey.isReadable()){
                    deserializeAndRespond(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    protected void register() throws IOException {
        System.out.println("Connection accepted");
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    protected void deserializeAndRespond(SelectionKey selectionKey) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        client.read(buffer);
        ObjectInputStream objectInputStream;

        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array())){
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            student = (Student) objectInputStream.readObject();
            buffer.clear();

            buffer = ByteBuffer.wrap("Receive".getBytes());

            System.out.println("Received: ");
            System.out.println(student.toString());

            client.write(buffer);
            buffer.clear();
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "Exception: ", ex);
        } finally {
            client.close();
            System.out.println("Client disconnected");
        }
    }
}
