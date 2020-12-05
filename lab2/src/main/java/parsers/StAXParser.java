package parsers;

import generated.classes.Paper;
import generated.classes.Periodical;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StAXParser extends AbstractParser{
    Periodical tempPeriodical = objectFactory.createPeriodical();

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
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("periodicals")){
                        tempPeriodical = objectFactory.createPeriodical();
                        data.clear();
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("title")){
                        xmlEvent =xmlEventReader.nextEvent();
                        data.put("title", xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("type")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("type",xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("monthly")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("monthly",xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("colorful")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("colorful",xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("pageAmount")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("pageAmount",xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("glossy")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("glossy",xmlEvent.asCharacters().getData());
                    }else if(startElement.getName().getLocalPart().equalsIgnoreCase("subscribable")){
                        xmlEvent = xmlEventReader.nextEvent();
                        data.put("subscribable",xmlEvent.asCharacters().getData());
                    }
                }

                if (xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();

                    if (endElement.getName().getLocalPart().equalsIgnoreCase("periodicals")){
                        objectBuilder.addPaper(data);
                    }
                }
            }


        } catch (XMLStreamException e) {
            logger.info("Stream problems");
        } catch (FileNotFoundException e) {
            logger.info("File wasn't found");
        }

        return getObject();
    }
}
