package parsers;

import generated.classes.Paper;
import generated.classes.Periodical;
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

public class DOMParser extends AbstractParser{
    public static void main(String[] args) {
        DOMParser domParser = new DOMParser();
        Paper result = domParser.parseXML("src/main/resources/periodicals.xml");
        for (Periodical i : result.getPeriodicals()){
            System.out.println(i);
        }
    }

    @Override
    public Paper parseXML(String filename) {
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

                    data.put("title", element.getElementsByTagName("title").item(0).getTextContent());
                    data.put("type", element.getElementsByTagName("type").item(0).getTextContent());
                    data.put("monthly", element.getElementsByTagName("monthly").item(0).getTextContent());
                    data.put("colorful", element.getElementsByTagName("colorful").item(0).getTextContent());
                    data.put("pageAmount", element.getElementsByTagName("pageAmount").item(0).getTextContent());

                    if (glossyParse.getLength() != 0) {
                        data.put("glossy", glossyParse.item(0).getTextContent());
                    }
                    if (subscribableParse.getLength() != 0) {
                        data.put("subscribable", subscribableParse.item(0).getTextContent());
                    }
                }

                objectBuilder.addPaper(data);
            }

        } catch (ParserConfigurationException e) {
            logger.error("Error occurred in parser configuration");
        } catch (SAXException e) {
            logger.error("Parsing error");
        } catch (IOException e) {
            logger.error("Major parsing error");
        }

        return getObject();
    }

}
