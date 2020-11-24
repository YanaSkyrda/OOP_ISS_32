package com.university.parser;

import com.university.gem.Gem;
import com.university.gem.VisualParameters;
import com.university.validator.Validator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StAXParser {

    public static List<Gem> parseXML(String fileName) {
        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        List<Gem> gemList = new ArrayList<>();
        Gem gem = new Gem();
        VisualParameters visualParameters = new VisualParameters();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "gem" -> {
                            gem = new Gem();
                            visualParameters = new VisualParameters();
                            Attribute id = startElement.getAttributeByName(new QName("id"));
                            if (id != null) {
                                gem.setId(id.getValue());
                            }
                        }
                        case "name" -> {
                            xmlEvent = reader.nextEvent();
                            gem.setName(xmlEvent.asCharacters().getData());
                        }
                        case "preciousness" -> {
                            xmlEvent = reader.nextEvent();
                            gem.setPreciousness(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                        }
                        case "origin" -> {
                            xmlEvent = reader.nextEvent();
                            gem.setOrigin((xmlEvent.asCharacters().getData()));
                        }
                        case "value" -> {
                            xmlEvent = reader.nextEvent();
                            gem.setValue(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "color" -> {
                            xmlEvent = reader.nextEvent();
                            visualParameters.setColor(xmlEvent.asCharacters().getData());
                        }
                        case "opacity" -> {
                            xmlEvent = reader.nextEvent();
                            visualParameters.setOpacity(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        }
                        case "edging" -> {
                            xmlEvent = reader.nextEvent();
                            visualParameters.setEdging(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("gem")) {
                        gem.setVisualParameters(visualParameters);
                        gemList.add(gem);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException exc) {
            exc.printStackTrace();
        }

        Collections.sort(gemList);

        return gemList;
    }
}
