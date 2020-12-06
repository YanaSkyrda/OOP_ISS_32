import java.io.Serializable;

public class Device implements Serializable {
    int width;
    int height;
    String name;

    public Device(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

}
