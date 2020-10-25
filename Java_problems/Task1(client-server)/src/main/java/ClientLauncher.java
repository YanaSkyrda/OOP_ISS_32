import catObject.Cat;
import client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientLauncher {
    private static Cat createCat() throws IOException {
        System.out.println("It's time to create your cat. Enter name and age.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return new Cat(reader.readLine(), Integer.parseInt(reader.readLine()));
    }

    public static void main(String[] args) {
        Cat cat;
        try {
            cat = createCat();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't create a cat.");
            return;
        }
        try {
            Client client = new Client(SocketChannel.open(new InetSocketAddress("localhost", 4004)), cat);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to server.");
        }
    }
}
