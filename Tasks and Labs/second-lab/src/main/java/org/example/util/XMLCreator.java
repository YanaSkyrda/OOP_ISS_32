package org.example.util;

import org.example.entity.Drug;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLCreator {
    private static final Logger log = Logger.getLogger(XMLCreator.class.getName());

    public void buildXML(List<Drug> drugList, String xmlFilePath) {
    try {
        File file = new File(xmlFilePath);
        if (file.createNewFile()) {
            log.info("File created: " + file.getName());
        } else {
            log.info(String.format("File %s already exists.", xmlFilePath));
        }
    } catch (IOException e) {
        log.log(Level.SEVERE, "Exception: ", e);
    }

    Collections.sort(drugList);

    try {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Medicine");
        document.appendChild(root);
        for (Drug drug : drugList) {
            Element drugElement = document.createElement("Drug");
            root.appendChild(drugElement);
            Element id = document.createElement("Id");
            id.appendChild(document.createTextNode(String.valueOf(drug.getId())));
            drugElement.appendChild(id);
            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(drug.getName()));
            drugElement.appendChild(name);
            Element pharm = document.createElement("Pharm");
            pharm.appendChild(document.createTextNode(drug.getPharmName()));
            drugElement.appendChild(pharm);
            Element group = document.createElement("Group");
            group.appendChild(document.createTextNode(String.valueOf(drug.getGroup())));
            drugElement.appendChild(group);
            Element analogs = document.createElement("Analogs");
            analogs.appendChild(document.createTextNode(drug.getAnalogs()));
            drugElement.appendChild(analogs);
            Element version = document.createElement("Version");
            drugElement.appendChild(version);
            Element type = document.createElement("Type");
            type.appendChild(document.createTextNode(drug.getVersion().getType()));
            version.appendChild(type);
            Element certificate = document.createElement("Certificate");
            certificate.appendChild(document.createTextNode(String.valueOf(drug.getVersion().getCertificate())));
            version.appendChild(certificate);
            Element aPackage = document.createElement("Package");
            aPackage.appendChild(document.createTextNode(drug.getVersion().getPackageType()));
            version.appendChild(aPackage);
            Element dosage = document.createElement("Dosage");
            dosage.appendChild(document.createTextNode(String.valueOf(drug.getVersion().getDosage())));
            version.appendChild(dosage);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}
