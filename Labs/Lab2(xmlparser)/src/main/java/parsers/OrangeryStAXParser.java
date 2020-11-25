package parsers;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OrangeryStAXParser extends XMLParser {
    private static final java.util.logging.Logger log = java.util.logging.Logger
            .getLogger(OrangeryStAXParser.class.getName());
    private XMLEventReader reader;

    private boolean checkIfTagIsComplexType(StartElement startElement) throws XMLStreamException {
        String startElementName = startElement.getName().getLocalPart();
        if (startElement.getName().getLocalPart().equals("Orangery")) {
            XMLEvent currEvent = reader.nextEvent();
            this.orangeryHandler.setField(currEvent.asStartElement().getName().getLocalPart(),
                    currEvent.asStartElement().getAttributeByName(new QName("code")).getValue());
            return true;
        } else if (startElementName.equals("flower")) {
            this.orangeryHandler.setField(startElementName,
                    startElement.getAttributeByName(new QName("code")).getValue());
            return true;
        } else if (startElementName.equals("visualParameters")
                || startElementName.equals("growingTips")) {
            this.orangeryHandler.setField(startElementName, null);
            return true;
        }
        return false;
    }

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
            reader = xmlInputFactory.createXMLEventReader(new FileInputStream(XMLFile));

            while (reader.hasNext()) {
                XMLEvent currEvent = reader.nextEvent();
                if (currEvent.isStartElement()) {
                    StartElement startElement = currEvent.asStartElement();
                    if (checkIfTagIsComplexType(startElement)) {
                        continue;
                    }

                    currEvent = reader.nextEvent();
                    setSimpleTagValue(currEvent, startElement);
                    this.orangeryHandler.setField(startElement.getName().getLocalPart(), null);
                }
            }

            XMLHelper.build(this.orangeryHandler.getOrangery(),
                    "src\\test\\java\\res\\StAXParserResult.xml");

        } catch (FileNotFoundException | XMLStreamException e) {
            log.severe(e.toString());
        }
    }
}
