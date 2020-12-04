
package parsers;
import model.Candy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMParser {

    public List<Candy> parse(File xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);
            document.getDocumentElement().normalize();
            Element rootNode = document.getDocumentElement();
            CandyHandler candyHandler = new CandyHandler();
            NodeList candiesNodesList = rootNode.getElementsByTagName(candyHandler.getName());
            for (int candiesNode = 0; candiesNode < candiesNodesList.getLength(); candiesNode++) {
                Element candyElement = (Element) candiesNodesList.item(candiesNode);
                traverseNodes(candyElement,candyHandler);
            }
            return candyHandler.getCandies();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
    private void traverseNodes(Node node, CandyHandler candyHandler) {
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Map<String, String> attributes = new HashMap<>();
            if(node.getAttributes() != null) {
                for (int i = 0; i < node.getAttributes().getLength(); i++) {
                    attributes.put(node.getAttributes().item(i).getNodeName(),
                            node.getAttributes().item(i).getTextContent());
                }
            }
            candyHandler.setField(node.getNodeName(), node.getTextContent(), attributes);
            if(node.getChildNodes() != null) {
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    traverseNodes(node.getChildNodes().item(i), candyHandler);
                }
            }
        }
    }

}
