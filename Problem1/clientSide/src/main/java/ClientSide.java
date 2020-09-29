import model.Cat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    private final Socket client;
    private final ObjectOutputStream out;

    private Cat cat;

    ClientSide(Socket client) throws IOException {                  //Adjustment 1.
        this.client = client;
        out = new ObjectOutputStream(client.getOutputStream());
    }
    //mockito capture (argumentCapture)

    public Cat getCat() {
        return cat;
    }

    public void run(Cat cat) throws IOException {                   //Adjustment 2.
        try {
            this.cat = cat;
            out.writeObject(cat);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null)
                out.close();
            if(client != null)
                client.close();
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public static void main(String[] args) throws IOException {
        ClientSide clientSide = new ClientSide(new Socket(InetAddress.getLocalHost(), 8188));
        clientSide.run(new Cat("Tom", "Black", 10));

    }
}
