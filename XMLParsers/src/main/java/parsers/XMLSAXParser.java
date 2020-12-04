package parsers;

import model.Candy;
import org.xml.sax.SAXException;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class XMLSAXParser {
    public List<Candy> parse(File xml) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        saxParser = factory.newSAXParser();
        CandyHandler userhandler = new CandyHandler();
        saxParser.parse(xml, userhandler);
        return userhandler.getCandies();
    }

}
