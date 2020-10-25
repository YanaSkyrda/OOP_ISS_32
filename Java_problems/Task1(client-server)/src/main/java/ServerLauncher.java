import server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 4004));
            serverSocket.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            Server server = new Server(serverSocket,  selector);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't start server.");
        }
    }
}
