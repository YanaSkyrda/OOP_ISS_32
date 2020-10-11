package client;

import java.io.IOException;

public class ClientLauncher {
    public static void main(String[] args) {
        try {
            Client client = new Client(4004);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to server.");
        }
    }
}
