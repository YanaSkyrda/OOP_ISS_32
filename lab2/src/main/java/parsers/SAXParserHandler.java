package parsers;

import generated.classes.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parsers.utils.ObjectBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParserHandler extends DefaultHandler {
    private ObjectFactory objectFactory = new ObjectFactory();
    private Paper paper = objectFactory.createPaper();
    private StringBuilder data;
    private TagsHandler tagsHandler = new TagsHandler();;
    private ObjectBuilder objectBuilder = new ObjectBuilder();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXParserHandler saxParserHandler = new SAXParserHandler();
        saxParser.parse("src/main/resources/periodicals.xml",saxParserHandler);

        Paper result = saxParserHandler.getObject();

        for (Periodical i : result.getPeriodicals()){
            System.out.println(i);
        }
    }

    public SAXParserHandler(){
        objectBuilder.resetID();
    }

    public Paper getObject(){
        tagsHandler.flush(objectBuilder);
        return objectBuilder.getPaper();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tagsHandler.add(qName,data.toString(),objectBuilder);
    }
}
