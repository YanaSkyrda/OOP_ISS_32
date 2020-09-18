package client;

import catObject.Cat;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Client extends Thread {
    private static SocketChannel client;
    private ByteBuffer buffer;
    private Selector selector;

    public Client(int port) {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", port));
            buffer = ByteBuffer.allocate(1024);
            selector = Selector.open();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String response = throwTheCat();
        System.out.println("Server response:" + response);
    }

    private String throwTheCat() {
        try {
            System.out.println("It's time to create your cat. Enter name and age.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Cat cat = new Cat(reader.readLine(), Integer.parseInt(reader.readLine()));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream writer = new ObjectOutputStream(byteArrayOutputStream);
            writer.writeObject(cat);
            writer.close();

            buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            client.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer answerBuffer = ByteBuffer.allocate(1024);
        try {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isReadable()) {
                    client.read(answerBuffer);
                }

                keyIterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String x = StandardCharsets.UTF_8.decode(answerBuffer).toString();
        return x;
    }
}
