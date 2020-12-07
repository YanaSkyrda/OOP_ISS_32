package parsers;

import generated.classes.Paper;
import generated.classes.Periodical;
import org.w3c.dom.*;
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

    private void parseElementsRecursive(Node element, TagsHandler handler,ObjectBuilder objectBuilder)  {
       handler.add(element.getNodeName(), element.getTextContent(),objectBuilder);

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE)
                parseElementsRecursive(child, handler,objectBuilder);
        }
    }
    
    @Override
    public Paper parseXML(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filename);
            Node rootNode = document.getDocumentElement();
            TagsHandler tagsHandler = new TagsHandler() ;
            objectBuilder = new ObjectBuilder();
            parseElementsRecursive(rootNode, tagsHandler, objectBuilder);
            tagsHandler.flush(objectBuilder);

        } catch (ParserConfigurationException e) {
            logger.info("Error occurred in parser configuration");
        } catch (SAXException e) {
            logger.info("Parsing error");
        } catch (IOException e) {
            logger.info ("Major parsing error");
        }

        return getObject();
    }
    
}
