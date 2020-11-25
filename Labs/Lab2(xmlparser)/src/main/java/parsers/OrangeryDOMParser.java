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

    private void setValueByTagName(Element element, String tagName) {
        this.orangeryHandler.setElementValue(element.getElementsByTagName(tagName)
                .item(0).getTextContent());
        this.orangeryHandler.setField(tagName);
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

                    this.orangeryHandler.setField("flower", element.getAttribute("code"));
                    this.orangeryHandler.setField("visualParameters", null);
                    this.orangeryHandler.setField("growingTips", null);

                    setValueByTagName(element, "name");
                    setValueByTagName(element, "origin");
                    setValueByTagName(element, "soil");
                    setValueByTagName(element, "multiplying");
                    setValueByTagName(element, "leavesColor");
                    setValueByTagName(element, "stalkColor");
                    setValueByTagName(element, "averageSize");
                    setValueByTagName(element, "watering");
                    setValueByTagName(element, "temperature");
                    setValueByTagName(element, "lightloving");
                }
            }

            XMLHelper.build(this.orangeryHandler.getOrangery(),
                    "src\\test\\java\\res\\DOMParserResult.xml");

        } catch (SAXException | ParserConfigurationException | IOException e) {
            log.severe(e.toString());
        }
    }
}
