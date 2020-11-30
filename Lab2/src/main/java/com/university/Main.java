package com.university;

import com.university.gem.Gem;
import com.university.parser.DOMParser;
import com.university.parser.GemHandler;
import com.university.parser.SAXParser;
import com.university.parser.StAXParser;
import com.university.validator.Validator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {

        System.out.println(Validator.validateDocument("Gem.xml", "Gem.xsd"));
        System.out.println(Validator.validateDocument("Gem.xml"));

        List<Gem> list1 = StAXParser.parseXML("Gem.xml");
        System.out.println(list1.toString());
        System.out.println("\n\n\n\n");

        List<Gem> list2 = DOMParser.parseXML("Gem.xml");
        System.out.println(list2.toString());
        System.out.println("\n\n\n\n");

        List<Gem> list3 = SAXParser.parseXML("Gem.xml");
        System.out.println(list3.toString());

    }
}
