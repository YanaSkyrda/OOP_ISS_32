package client;

public class ClientLauncher {
    public static void main(String[] args) {
        Client client = new Client(4004);
        client.run();
    }
}
