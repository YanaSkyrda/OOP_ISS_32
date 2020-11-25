import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.Test;
import parsers.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParsersTest {
    private OrangerySAXParser saxParser = new OrangerySAXParser();
    private OrangeryStAXParser staxParser = new OrangeryStAXParser();
    private OrangeryDOMParser domParser = new OrangeryDOMParser();

    private String XSD = "src\\main\\resources\\orangery.xsd";
    private String XMLPath = "src\\test\\java\\res\\expected.xml";
    private File XML = new File("src\\test\\java\\res\\expected.xml");

    @Test
    void shouldCreateCorrectFileWithDOMParser() throws IOException {
        domParser.parse(XML);
        assert(XMLHelper.isValid("src\\test\\java\\res\\DOMParserResult.xml", XSD));
        assert(FileUtils.contentEquals(new File("src\\test\\java\\res\\DOMParserResult.xml"),
                new File(XMLPath)));
    }

    @Test
    void shouldCreateCorrectFileWithSAXParser() throws IOException {
        saxParser.parse(XML);
        assert(XMLHelper.isValid("src\\test\\java\\res\\SAXParserResult.xml", XSD));
        assert(FileUtils.contentEquals(new File("src\\test\\java\\res\\SAXParserResult.xml"),
                new File(XMLPath)));
    }

    @Test
    void shouldCreateCorrectFileWithStAXParser() throws IOException {
        staxParser.parse(XML);
        assert (XMLHelper.isValid("src\\test\\java\\res\\StAXParserResult.xml", XSD));
        assert (FileUtils.contentEquals(new File("src\\test\\java\\res\\StAXParserResult.xml"),
                new File(XMLPath)));
    }
}
