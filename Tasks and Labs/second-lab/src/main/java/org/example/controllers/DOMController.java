package org.example.controllers;

import org.example.entity.*;
import org.example.entity.Package;
import org.example.util.Constants;
import org.example.util.XML;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DOMController {
    private final String xmlFileName;

    private Medicine drugList;

    private static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Medicine getDrugs() {
        return drugList;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        if(validate){
            BUILDER_FACTORY.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
            BUILDER_FACTORY.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }
        BUILDER_FACTORY.setNamespaceAware(true);

        DocumentBuilder db = BUILDER_FACTORY.newDocumentBuilder();

        Document document = db.parse(xmlFileName);

        Element root = document.getDocumentElement();

        drugList = new Medicine();

        NodeList drugNodes = root.
                getElementsByTagName(XML.DRUG.value());

        for (int i = 0; i < drugNodes.getLength(); i++) {
            Drug drug = getDrug(drugNodes.item(i));
            drugList.getMedicineList().add(drug);
        }
    }

    /**
     * Extracts bank object from the bank XML node.
     *
     * @param item
     *            Bank node.
     * @return Bank object.
     */
    private static Drug getDrug(Node item) {
        Drug drug = new Drug();
        Element drugElement = (Element) item;

        Node drugNode = drugElement.getElementsByTagName(XML.NAME.value()).item(0);
        drug.setName(drugNode.getTextContent());

        drugNode = drugElement.getElementsByTagName(XML.PHARM.value()).item(0);
        drug.setPharmName(drugNode.getTextContent());

        drugNode = drugElement.getElementsByTagName(XML.GROUP.value()).item(0);
        drug.setGroup(drugNode.getTextContent());

        drugNode = drugElement.getElementsByTagName(XML.ANALOGS.value()).item(0);
        drug.setAnalogs(drugNode.getTextContent());

        drug.setVersion(getVersionNode(drugElement));

        return drug;
    }

    private static Version getVersionNode(Element drugElement) {
        Version version = new Version();
        Node typeNode = drugElement.getElementsByTagName(XML.TYPE.value()).item(0);
        version.setType(typeNode.getTextContent());
        version.setCertificate(getCert(drugElement));
        version.setPackageType(getPack(drugElement));
        version.setDosage(getDos(drugElement));
        return version;
    }

    private static Dosage getDos(Element drugElement) {
        Dosage dosage = new Dosage();
        Node dosageNode = drugElement.getElementsByTagName(XML.DOSAGE.value()).item(0);
        Element aElement = (Element) dosageNode;

        // process attributes
        String dosageAmount = aElement.getAttribute(XML.DOSAGEAMOUNT.value());
        dosage.setAmount(Double.parseDouble(dosageAmount));
        String periodicity = aElement.getAttribute(XML.PERIODICITY.value());
        dosage.setPeriodicity(Integer.parseInt(periodicity));
        return dosage;
    }

    private static Package getPack(Element drugElement) {
        Package aPackage = new Package();
        Node packNode = drugElement.getElementsByTagName(XML.PACKAGE.value()).item(0);
        Element aElement = (Element) packNode;

        // process attributes
        String packType = aElement.getAttribute(XML.PACKTYPE.value());
        aPackage.setType(packType);
        String amount = aElement.getAttribute(XML.AMOUNT.value());
        aPackage.setAmount(Integer.parseInt(amount));
        String price = aElement.getAttribute(XML.PRICE.value());
        aPackage.setPrice(Double.parseDouble(price));
        return aPackage;
    }

    private static Certificate getCert(Element drugElement){
        Certificate certificate = new Certificate();
        Node certNode = drugElement.getElementsByTagName(XML.CERTIFICATE.value()).item(0);
        Element aElement = (Element) certNode;

        // process attributes
        String correct = aElement.getAttribute(XML.NUMBER.value());
        certificate.setNumber(Integer.parseInt(correct));
        String created = aElement.getAttribute(XML.CREATED.value());
        certificate.setCreated(Integer.parseInt(created));
        String till = aElement.getAttribute(XML.TILL.value());
        certificate.setTill(Integer.parseInt(till));
        String organization = aElement.getAttribute(XML.ORGANIZATION.value());
        certificate.setOrganization(organization);
        return certificate;
    }

    /**
     * Creates and returns DOM of the Test container.
     *
     * @param drugList
     *            Test object.
     * @throws ParserConfigurationException
     */
    public static Document getDocument(Medicine drugList)
            throws ParserConfigurationException {
        DOCUMENT_BUILDER_FACTORY.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);

        DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        Document document = db.newDocument();

        Element mElement = document.createElement(XML.MEDICINE.value());
        document.appendChild(mElement);

        for (Drug drug : drugList.getMedicineList()) {
            Element drugElement = document.createElement(XML.DRUG.value());
            mElement.appendChild(drugElement);

            Element nameElement = document.createElement(XML.NAME.value());
            nameElement.setTextContent(drug.getName());
            drugElement.appendChild(nameElement);

            Element pharmElement = document.createElement(XML.PHARM.value());
            pharmElement.setTextContent(drug.getPharmName());
            drugElement.appendChild(pharmElement);

            Element groupElement = document.createElement(XML.GROUP.value());
            groupElement.setTextContent(drug.getGroup());
            drugElement.appendChild(groupElement);

            Element analogsElement = document.createElement(XML.ANALOGS.value());
            analogsElement.setTextContent(drug.getAnalogs());
            drugElement.appendChild(analogsElement);

            Element versionElement = document.createElement(XML.VERSION.value());
            drugElement.appendChild(versionElement);

                Element typeElement = document.createElement(XML.TYPE.value());
                typeElement.setTextContent(drug.getVersion().getType());
                versionElement.appendChild(typeElement);

                Element sertElement = document.createElement(XML.CERTIFICATE.value());
                sertElement.setAttribute(XML.NUMBER.value(), Integer.toString(drug.getVersion().getCertificate().getNumber()));
                sertElement.setAttribute(XML.CREATED.value(), Integer.toString(drug.getVersion().getCertificate().getCreated()));
                sertElement.setAttribute(XML.TILL.value(), Integer.toString(drug.getVersion().getCertificate().getTill()));
                sertElement.setAttribute(XML.ORGANIZATION.value(), drug.getVersion().getCertificate().getOrganization());
                versionElement.appendChild(sertElement);

                Element packElement = document.createElement(XML.PACKAGE.value());
                packElement.setAttribute(XML.PACKTYPE.value(), drug.getVersion().getPackageType().getType());
                packElement.setAttribute(XML.AMOUNT.value(), Integer.toString(drug.getVersion().getPackageType().getAmount()));
                packElement.setAttribute(XML.PRICE.value(), Double.toString(drug.getVersion().getPackageType().getPrice()));
                versionElement.appendChild(packElement);

                Element dosageElement = document.createElement(XML.DOSAGE.value());
                dosageElement.setAttribute(XML.DOSAGEAMOUNT.value(), Double.toString(drug.getVersion().getDosage().getAmount()));
                dosageElement.setAttribute(XML.PERIODICITY.value(), Integer.toString(drug.getVersion().getDosage().getPeriodicity()));
                versionElement.appendChild(dosageElement);
        }
        return document;
    }

    /**
     * Saves Test object to XML file.
     *
     * @param drugList
     *            Test object to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */
    public static void saveToXML(Medicine drugList, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        saveToXML(getDocument(drugList), xmlFileName);
    }

    /**
     * Save DOM to XML.
     *
     * @param document
     *            DOM to be saved.
     * @param xmlFileName
     *            Output XML file name.
     */
    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException{
        StreamResult result = new StreamResult(new File(xmlFileName));
        Transformer t = TRANSFORMER_FACTORY.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document), result);
    }
}
