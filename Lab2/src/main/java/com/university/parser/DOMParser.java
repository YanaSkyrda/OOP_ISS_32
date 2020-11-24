package com.university.parser;

import com.university.gem.Gem;
import com.university.gem.VisualParameters;
import com.university.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DOMParser {

    public static List<Gem> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = documentBuilder.parse(new File(fileName));

        NodeList gemList = doc.getElementsByTagName("gem");
        NodeList visualParametersList = doc.getElementsByTagName("visualParameters");

        String gemId, gemName, gemOrigin, gemColor;
        int gemOpacity, gemEdging;
        double gemValue;
        boolean gemPreciousness;

        List<Gem> list = new ArrayList<>();

        int i = 0;

        while (i < gemList.getLength()){
            Element element1 = (Element) gemList.item(i);

            gemId = ((Element) gemList.item(i)).getAttribute("id");
            gemName = element1.getElementsByTagName("name").item(0).getTextContent();
            gemPreciousness = Boolean.parseBoolean(element1.getElementsByTagName("preciousness").item(0).getTextContent());
            gemOrigin = element1.getElementsByTagName("origin").item(0).getTextContent();
            gemValue = Double.parseDouble(element1.getElementsByTagName("value").item(0).getTextContent());

            Element element2 = (Element) visualParametersList.item(i);

            gemColor = element2.getElementsByTagName("color").item(0).getTextContent();
            gemOpacity = Integer.parseInt(element2.getElementsByTagName("opacity").item(0).getTextContent());
            gemEdging = Integer.parseInt(element2.getElementsByTagName("edging").item(0).getTextContent());

            VisualParameters visualParameters = new VisualParameters(gemColor, gemOpacity, gemEdging);
            Gem gem = new Gem(gemId, gemName, gemPreciousness, gemOrigin, visualParameters, gemValue);

            list.add(gem);

            i++;
        }

        Collections.sort(list);

        return list;
    }
}
