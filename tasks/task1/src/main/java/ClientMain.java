import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientMain {
    private static Device createDevice() throws IOException {
        System.out.println("It's time to create your device");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter name: ");
        String name = reader.readLine();
        System.out.println("Enter width: ");
        int width =Integer.parseInt(reader.readLine());
        System.out.println("Enter height: ");
        int height =Integer.parseInt(reader.readLine());
        return new Device(name, width, height);
    }

    public static void main(String[] args) {
        Device device;
        try {
            device = createDevice();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't create a device.");
            return;
        }
        try {
            Client client = new Client(SocketChannel.open(new InetSocketAddress("localhost", 9000)), device);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to server.");
        }
    }
}
