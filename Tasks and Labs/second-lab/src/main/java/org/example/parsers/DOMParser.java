package org.example.parsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.example.entity.Drug;
import org.example.entity.Group;
import org.example.entity.Version;
import org.example.util.XMLCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DOMParser extends ParserXML {
    private static final Logger log = Logger.getLogger(DOMParser.class.getName());

    public DOMParser(XMLCreator xmlCreator) {
        this.xmlCreator = xmlCreator;
    }

    @Override
    public void parse(String XMLFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XMLFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Drug");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                drugHandler.getMedicine().getDrugList().add(new Drug());
                drugHandler.lastestDrug().setVersion(new Version());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    drugHandler.lastestDrug().setId(Integer.parseInt(eElement.getElementsByTagName("Id")
                            .item(0)
                            .getTextContent()));
                    drugHandler.lastestDrug().setName(eElement.getElementsByTagName("Name")
                            .item(0)
                            .getTextContent());
                    drugHandler.lastestDrug().setPharmName(eElement.getElementsByTagName(
                            "Pharm")
                            .item(0)
                            .getTextContent());
                    drugHandler.lastestDrug().setGroup(Group.valueOf(eElement.getElementsByTagName(
                            "Group")
                            .item(0)
                            .getTextContent()));
                    drugHandler.lastestDrug().setAnalogs(eElement.getElementsByTagName(
                            "Analogs")
                            .item(0)
                            .getTextContent());
                    drugHandler.lastestDrug().getVersion().setType(eElement.getElementsByTagName("Type")
                                    .item(0)
                                    .getTextContent());
                    drugHandler.lastestDrug().getVersion().setCertificate(Integer
                            .parseInt(eElement.getElementsByTagName("Certificate")
                                    .item(0)
                                    .getTextContent()));
                    drugHandler.lastestDrug().getVersion().setPackageType(
                            eElement.getElementsByTagName("Package")
                                    .item(0)
                                    .getTextContent());
                    drugHandler.lastestDrug().getVersion().setDosage(Double
                            .parseDouble(eElement.getElementsByTagName("Dosage")
                                    .item(0)
                                    .getTextContent()));

                }
            }
            xmlCreator.buildXML(drugHandler.getMedicine().getDrugList(),"src/main/resources/dom.xml");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}
