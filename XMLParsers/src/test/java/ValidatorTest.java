import org.junit.jupiter.api.Test;
import parsers.SchemeValidator;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void validateCorrectFile() {
        assertTrue(SchemeValidator.validate("src/main/resources/candy.xml", "src/main/resources/candy.xsd"));

    }

}
