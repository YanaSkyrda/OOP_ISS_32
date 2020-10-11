package server;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't start server.");
        }
    }
}
