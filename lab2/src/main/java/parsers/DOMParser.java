package parsers;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import generated.classes.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parsers.utils.ObjectBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DOMParser {
    private static final Logger logger = LoggerFactory.getLogger(DOMParser.class);

    private HashMap<String, String> values = new HashMap<>();
    private ObjectBuilder objectBuilder;

    public static void main(String[] args) {
        DOMParser domParser = new DOMParser("src/main/resources/periodicals.xml");
    }

    public DOMParser(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filename);
            Node rootNode = document.getDocumentElement();

            if (rootNode.getNodeName() != "Paper") {
                throw new SAXException();
            }

            objectBuilder = new ObjectBuilder();

            NodeList nodeList = document.getElementsByTagName("periodicals");
            Node tempNode;

            for (int i = 0, length = nodeList.getLength(); i < length; ++i) {
                tempNode = nodeList.item(i);
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) tempNode;
                    NodeList glossyParse = element.getElementsByTagName("glossy");
                    NodeList subscribableParse = element.getElementsByTagName("subscribable");

                    values.put("title", element.getElementsByTagName("title").item(0).getTextContent());
                    values.put("type", element.getElementsByTagName("type").item(0).getTextContent());
                    values.put("monthly", element.getElementsByTagName("monthly").item(0).getTextContent());
                    values.put("colorful", element.getElementsByTagName("colorful").item(0).getTextContent());
                    values.put("pageAmount", element.getElementsByTagName("pageAmount").item(0).getTextContent());

                    if (glossyParse.getLength() != 0) {
                        values.put("glossy", glossyParse.item(0).getTextContent());
                    }
                    if (subscribableParse.getLength() != 0) {
                        values.put("subscribable", subscribableParse.item(0).getTextContent());
                    }
                }

                objectBuilder.addPaper(values);
            }

            for (Periodical i : objectBuilder.getPaper().getPeriodicals()){
                System.out.println(i);
            }
        } catch (ParserConfigurationException e) {
            logger.error("Error occurred in parser configuration");
        } catch (SAXException e) {
            logger.error("Parsing error");
        } catch (IOException e) {
            logger.error("Major parsing error");
        }
    }

    public List<Periodical> getObject() {
        return objectBuilder.getPaper().getPeriodicals();
    }
}
