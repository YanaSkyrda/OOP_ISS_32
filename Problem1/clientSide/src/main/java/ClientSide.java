import model.Cat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    private Socket client = null;
    private ObjectOutputStream out = null;

    private Cat cat;

    ClientSide() throws IOException {
        client = new Socket(InetAddress.getLocalHost(), 8188);
        out = new ObjectOutputStream(client.getOutputStream());
    }

    public Cat getCat() {
        return cat;
    }

    public void run(Cat cat) throws IOException {
        try {
            this.cat = cat;
            out.writeObject(cat);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            //return false;
        } finally {
            if(out != null)
                out.close();
            if(client != null)
                client.close();
        }
    }
    public static void main(String[] args) throws IOException {
        ClientSide clientSide = new ClientSide();
        clientSide.run(new Cat("Tom", "Black", 10));

    }
}
