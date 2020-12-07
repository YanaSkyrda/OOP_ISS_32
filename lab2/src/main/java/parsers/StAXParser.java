package parsers;

import generated.classes.Paper;
import generated.classes.Periodical;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StAXParser extends AbstractParser{
    TagsHandler tagsHandler = new TagsHandler();

    public static void main(String[] args) {
        StAXParser stAXParser = new StAXParser();
        Paper result = stAXParser.parseXML("src/main/resources/periodicals.xml");
        for (Periodical i : result.getPeriodicals()){
            System.out.println(i);
        }
    }

    @Override
    public Paper parseXML(String filename) {
        XMLInputFactory  xmlInputFactory= XMLInputFactory.newInstance();

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filename));

            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if(xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();
                    String qName = startElement.getName().toString();

                    xmlEvent = xmlEventReader.peek();
                    if (xmlEvent != null && xmlEvent.isCharacters()) {
                        Characters characters = xmlEventReader.nextEvent().asCharacters();
                        System.out.println(qName + " " + characters.getData());
                        tagsHandler.add(qName,characters.getData(),objectBuilder);
                    }
                }
            }

            tagsHandler.flush(objectBuilder);

        } catch (XMLStreamException e) {
            logger.info("Stream problems");
        } catch (FileNotFoundException e) {
            logger.info("File wasn't found");
        }

        return getObject();
    }
}
