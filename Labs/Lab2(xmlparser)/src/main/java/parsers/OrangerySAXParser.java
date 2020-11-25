package parsers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.IOException;

public class OrangerySAXParser extends XMLParser {
    private static final java.util.logging.Logger log = java.util.logging.Logger
            .getLogger(OrangerySAXParser.class.getName());

    @Override
    public void parse(File XMLFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(XMLFile, this.orangeryHandler);

            XMLHelper.build(this.orangeryHandler.getOrangery(),
                    "src\\test\\java\\res\\SAXParserResult.xml");

        } catch (SAXException | ParserConfigurationException | IOException e) {
            log.severe(e.toString());
        }
    }
}
