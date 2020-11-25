package parsers;

import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import targetclasses.Flower;
import targetclasses.Orangery;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class XMLHelper {
    private static final java.util.logging.Logger log = java.util.logging.Logger
            .getLogger(XMLHelper.class.getName());

    public static void build(Orangery orangery, String xmlFilePath) {
        List<Flower> flowers = orangery.getFlower();
        try {
            File file = new File(xmlFilePath);
            if (file.createNewFile()) {
                log.info("XML file created: " + file.getName());
            } else {
                log.info("XML file " + xmlFilePath + " already exists.");
            }
        } catch (IOException e) {
            log.severe(e.toString());
        }

        Collections.sort(flowers);

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElementNS("targetClasses","Orangery");
            document.appendChild(root);

            for (Flower flower : flowers) {
                Element flowerElement = document.createElement("flower");

                root.appendChild(flowerElement);

                Attr attr = document.createAttribute("code");
                attr.setValue(flower.getCode().toString());
                flowerElement.setAttributeNode(attr);

                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(flower.getName()));
                flowerElement.appendChild(name);

                Element soil = document.createElement("soil");
                soil.appendChild(document.createTextNode(String.valueOf(flower.getSoil())));
                flowerElement.appendChild(soil);

                Element origin = document.createElement("origin");
                origin.appendChild(document.createTextNode(flower.getOrigin().toString()));
                flowerElement.appendChild(origin);

                Element visualParameters = document.createElement("visualParameters");
                flowerElement.appendChild(visualParameters);

                Element stalkColor = document.createElement("stalkColor");
                stalkColor.appendChild(document.createTextNode(
                        String.valueOf(flower.getVisualParameters().getStalkColor())));
                visualParameters.appendChild(stalkColor);

                Element leavesColor = document.createElement("leavesColor");
                leavesColor.appendChild(document.createTextNode(
                        String.valueOf(flower.getVisualParameters().getLeavesColor())));
                visualParameters.appendChild(leavesColor);

                Element averageSize = document.createElement("averageSize");
                averageSize.appendChild(document.createTextNode(
                        String.valueOf(flower.getVisualParameters().getAverageSize())));
                visualParameters.appendChild(averageSize);

                Element growingTips = document.createElement("growingTips");
                flowerElement.appendChild(growingTips);

                Element temperature = document.createElement("temperature");
                temperature.appendChild(document.createTextNode(
                        String.valueOf(flower.getGrowingTips().getTemperature())));
                growingTips.appendChild(temperature);

                Element lightloving = document.createElement("lightloving");
                lightloving.appendChild(document.createTextNode(
                        String.valueOf(flower.getGrowingTips().isLightloving())));
                growingTips.appendChild(lightloving);

                Element watering = document.createElement("watering");
                watering.appendChild(document.createTextNode(
                        String.valueOf(flower.getGrowingTips().getWatering())));
                growingTips.appendChild(watering);

                Element multiplying = document.createElement("multiplying");
                multiplying.appendChild(document.createTextNode(flower.getMultiplying().toString()));
                flowerElement.appendChild(multiplying);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            log.severe(e.toString());
        }
    }

    public static boolean isValid(String XMLFilePath, String XSDFilePath) {
        File XMLFile = new File(XMLFilePath);
        File XSDFile = new File(XSDFilePath);
        if (!FileUtils.getExtension(XMLFile.getName()).equals("xml")) {
            throw new IllegalArgumentException("Expected XML file");
        }
        if (!FileUtils.getExtension(XSDFile.getName()).equals("xsd")) {
            throw new IllegalArgumentException("Expected XSD file");
        }
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(XSDFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(XMLFile));
        } catch (IOException | SAXException e) {
            log.severe("Validation error, reason: " + e.getMessage());
            return false;
        }
        return true;
    }
}
