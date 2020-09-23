import model.Cat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    ServerSocket server = null;
    Socket client = null;

    ObjectOutputStream out = null;
    ObjectInputStream in = null;

    Cat cat = null;

    ServerSide() throws IOException {

        cat = new Cat();

        server = new ServerSocket(8188);
        client = server.accept();

        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
    }

    public Cat getCat() {
        return cat;
    }

    public void run() throws IOException {
        try {
            cat = (Cat) in.readObject();
            System.out.println(cat.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //return false;
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (client != null)
                client.close();
            if (server != null)
                server.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSide serverSide = new ServerSide();
        serverSide.run();

    }
}
