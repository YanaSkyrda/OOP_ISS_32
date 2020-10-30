import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomClassLoaderTest {
    @Test
    void testClassLoader() {
        CustomClassLoader classLoader = new CustomClassLoader();
        Class rectangle = classLoader.findClass("model.Rectangle");
        assertEquals(rectangle.getName(), "model.Rectangle");
        assertEquals(rectangle.getSuperclass().getName(),"model.Line");
    }
}