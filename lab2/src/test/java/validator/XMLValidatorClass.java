package validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XMLValidatorClass {
    private XMLValidator xmlValidator = new XMLValidator();

    @Test
    @DisplayName("Check for valid xml file")
    void validXMLFileCheck(){
        assertTrue(xmlValidator.isValid("src/main/resources/periodicals.xml","src/main/resources/periodicals.xsd"));
    }

    @Test
    @DisplayName("Check for not valid xml file")
    void notValidXMLFileCheck(){
        assertFalse(xmlValidator.isValid("src/main/resources/notValidPeriodicals.xml","src/main/resources/periodicals.xsd"));
    }
}
