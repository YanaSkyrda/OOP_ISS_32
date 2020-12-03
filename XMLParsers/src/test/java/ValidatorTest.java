import org.junit.jupiter.api.Test;
import parsers.SchemeValidator;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void validateCorrectFile() {
        assertTrue(SchemeValidator.validate("/Users/mykolamedynsky/Desktop/oop-java/OOP_ISS_32/XMLParsers/src/main/resources/candy.xml", "/Users/mykolamedynsky/Desktop/oop-java/OOP_ISS_32/XMLParsers/src/main/resources/candy.xsd"));

    }

}
