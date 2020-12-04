package parsers;

import model.Candy;


import org.xml.sax.Attributes;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class StAXParser {
    public List<Candy> parse(File xml) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader;

        CandyHandler candiesHandler = new CandyHandler();
        reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xml));

        while (reader.hasNext()) {

            XMLEvent nextEvent = reader.nextEvent();


            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();


                nextEvent = reader.nextEvent();
                String name = startElement.getName().getLocalPart();

                if (nextEvent.isCharacters()) {
                    List<Attribute> attributes = new ArrayList<>();
                    Iterator<Attribute> iterator = startElement.getAttributes();
                    while (iterator.hasNext()) {
                        attributes.add(iterator.next());

                    }

                    Map<String, String> attributeMap = new HashMap<>();

                    for(Attribute attribute: attributes) {
                        attributeMap.put(
                                attribute.getName().getLocalPart(),
                                attribute.getValue()
                        );
                    }
                    candiesHandler.setField(name, nextEvent.asCharacters().getData(), attributeMap);
                }
            }
        }


        return candiesHandler.getCandies();
    }

}
