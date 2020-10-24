package org.example.parsers;

import org.example.util.XMLCreator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class STAXParser extends ParserXML {
    private static final Logger log = Logger.getLogger(STAXParser.class.getName());

    public STAXParser(XMLCreator xmlCreator) {
        this.xmlCreator = xmlCreator;
    }

    @Override
    public void parse(String XMLFile) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader;
        try {
            reader = xmlInputFactory.createXMLEventReader(new FileInputStream(XMLFile));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String attributeString = null;
                    nextEvent = reader.nextEvent();
                    String string = startElement.getName().getLocalPart();
                    if (nextEvent.isCharacters()) {
                        drugHandler.setElementValue(nextEvent.asCharacters().getData());
                        drugHandler.setField(string, attributeString);
                    }
                }
            }
            xmlCreator.buildXML(drugHandler.getMedicine().getDrugList(), "src/main/resources/stax.xml");
        } catch (FileNotFoundException | XMLStreamException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}
