import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;
public class ClassInfoTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Test
    void testPrintInfo(){

        ClassInfo info = new ClassInfo("model.Rectangle");
        info.printInfo();
        assertNotNull(outContent);
    }
}
