package client;

import catObject.Cat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            Client client = new Client(4004, cat);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to server.");
        }
    }
}
