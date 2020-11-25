package parsers;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OrangeryStAXParser extends XMLParser {
    private static final java.util.logging.Logger log = java.util.logging.Logger
            .getLogger(OrangeryStAXParser.class.getName());

    private void setSimpleTagValue(XMLEvent currEvent, StartElement startElement) {
        if (currEvent.isCharacters()) {
            this.orangeryHandler.setElementValue(currEvent.asCharacters().getData());
            this.orangeryHandler.setField(startElement.getName().getLocalPart());
        }
    }

    @Override
    public void parse(File XMLFile) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(XMLFile));

            while (reader.hasNext()) {
                XMLEvent currEvent = reader.nextEvent();
                if (currEvent.isStartElement()) {
                    StartElement startElement = currEvent.asStartElement();

                    XMLEvent nextEvent = reader.peek();
                    if (nextEvent.isCharacters()) {
                        setSimpleTagValue(nextEvent, startElement);
                        this.orangeryHandler.setField(startElement.getName().getLocalPart());
                        reader.nextEvent();
                    } else {
                        String attribute = null;
                        if (startElement.getAttributes().hasNext()) {
                            attribute = ((Attribute) startElement.getAttributes().next()).getValue();
                        }
                        this.orangeryHandler.setField(startElement.getName().getLocalPart(), attribute);
                    }
                }
            }

            XMLHelper.build(this.orangeryHandler.getOrangery(),
                    "src\\test\\java\\res\\StAXParserResult.xml");

        } catch (FileNotFoundException | XMLStreamException e) {
            log.severe(e.toString());
        }
    }
}
