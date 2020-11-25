package parsers;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

public class OrangeryDOMParser extends XMLParser {
    private static final java.util.logging.Logger log = java.util.logging.Logger
            .getLogger(OrangeryDOMParser.class.getName());

    private Document setupDocument(File XMLFile)
            throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(XMLFile);
        document.getDocumentElement().normalize();
        return document;
    }

    private static String getChildValue(Element element, String name) {
        Element child = (Element) element.getElementsByTagName(name).item(0);
        if (child == null) {
            return "";
        }
        Node node = child.getFirstChild();
        return node.getNodeValue();
    }

    @Override
    public void parse(File XMLFile) {
        try {
            Document document = setupDocument(XMLFile);
            NodeList nodesList = document.getElementsByTagName("flower");
            for (int i = 0; i < nodesList.getLength(); i++) {
                Node node = nodesList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    orangeryHandler.setField(element.getNodeName(),
                            element.getAttributes().item(0).getNodeValue());
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element child = (Element) childNodes.item(j);
                            orangeryHandler.setElementValue(getChildValue(element, child.getNodeName()));
                            orangeryHandler.setField(child.getNodeName(), null);
                            NodeList childChildNodes = child.getChildNodes();
                            for (int k = 0; k < childChildNodes.getLength(); k++) {
                                if (childChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                    Element childChild = (Element) childChildNodes.item(k);
                                    orangeryHandler.setElementValue(getChildValue(child, childChild.getNodeName()));
                                    orangeryHandler.setField(childChild.getNodeName());
                                }
                            }
                        }
                    }
                }
            }

            XMLHelper.build(this.orangeryHandler.getOrangery(),
                    "src\\test\\java\\res\\DOMParserResult.xml");

        } catch (SAXException | ParserConfigurationException | IOException e) {
            log.severe(e.toString());
        }
    }
}
