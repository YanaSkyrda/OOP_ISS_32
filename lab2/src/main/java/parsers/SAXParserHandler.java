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
import java.util.HashMap;
import java.util.HashSet;

public class SAXParserHandler extends DefaultHandler {
    private ObjectFactory objectFactory = new ObjectFactory();
    private Paper paper = objectFactory.createPaper();
    private HashSet<String> hashSet;
    private HashMap<String, String> hashMap;
    private StringBuilder data;

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
        return objectBuilder.getPaper();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("periodicals")) {
            hashSet = new HashSet<>();
            hashMap = new HashMap<>();
        } else if (qName.equalsIgnoreCase("title")) {
            hashSet.add("title");
        } else if (qName.equalsIgnoreCase("type")) {
            hashSet.add("type");
        } else if (qName.equalsIgnoreCase("monthly")) {
            hashSet.add("monthly");
        } else if (qName.equalsIgnoreCase("colorful")) {
            hashSet.add("colorful");
        } else if (qName.equalsIgnoreCase("pageAmount")) {
            hashSet.add("pageAmount");
        } else if (qName.equalsIgnoreCase("glossy")) {
            hashSet.add("glossy");
        } else if (qName.equalsIgnoreCase("subscribable")) {
            hashSet.add("subscribable");
        }

        data = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (hashSet.contains("title")) {
            hashMap.put("title", data.toString());
            hashSet.remove("title");
        } else if (hashSet.contains("type")) {
            hashMap.put("type", data.toString());
            hashSet.remove("type");
        } else if (hashSet.contains("monthly")) {
            hashMap.put("monthly", data.toString());
            hashSet.remove("monthly");
        } else if (hashSet.contains("colorful")) {
            hashMap.put("colorful", data.toString());
            hashSet.remove("colorful");
        } else if (hashSet.contains("pageAmount")) {
            hashMap.put("pageAmount", data.toString());
            hashSet.remove("pageAmount");
        } else if (hashSet.contains("glossy")) {
            hashMap.put("glossy", data.toString());
            hashSet.remove("glossy");
        } else if (hashSet.contains("subscribable")) {
            hashMap.put("subscribable", data.toString());
            hashSet.remove("subscribable");
        }

        if (qName.equalsIgnoreCase("periodicals")) {
            objectBuilder.addPaper(hashMap);
        }
    }
}
