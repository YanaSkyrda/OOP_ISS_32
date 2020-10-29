package org.example;

import org.example.parsers.*;
import org.example.util.XMLCreator;
import org.example.util.XMLValidator;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        XMLCreator xmlCreator = new XMLCreator();
        DOMParser domParser = new DOMParser(xmlCreator);
        SAXDrugParser saxDrugParser = new SAXDrugParser(xmlCreator);
        STAXParser staxParser = new STAXParser(xmlCreator);

        String XML = "src/main/resources/input.xml";
        String XSD = "src/main/resources/input.xsd";
        if(XMLValidator.validateXML(XML, XSD)){
            log.info("XML is valid");
        }
        else {
            log.info("XML is not valid");
        }
        saxDrugParser.parse(XML);
        saxDrugParser.createXML();
        staxParser.parse(XML);
        staxParser.createXML();
        domParser.parse(XML);
        domParser.createXML();
    }
}
