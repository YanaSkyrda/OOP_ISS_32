package validator;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {
    private final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    private static final Logger logger = LoggerFactory.getLogger(XMLValidator.class);

    public static void main(String[] args) {
        System.out.println(new XMLValidator().isValid("src/main/resources/periodicals.xml","src/main/resources/periodicals.xsd"));
    }

    public boolean isValid(String xmlFilename, String xsdFilename) {
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(new File(xsdFilename));

        } catch (SAXException e) {
            logger.error("Schema creation error, possible fault: wrong filename");
            return false;
        }

        Validator validator = schema.newValidator();

        try {
            validator.validate(new StreamSource(xmlFilename));
        } catch (SAXException e) {
            logger.error("Validation error");
            return false;
        } catch (IOException e) {
            logger.error("IO error");
            return false;
        }

        return true;
    }
}
