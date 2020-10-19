package org.example.controllers;

import org.example.entity.*;
import org.example.entity.Package;
import org.example.util.Constants;
import org.example.util.XML;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXController extends DefaultHandler {
    private final String xmlFileName;

    private String currentElement;

    private Medicine drugList;

    private Drug drug;

    private Version version;

    private Certificate certificate;

    private Dosage dosage;

    private Package aPackage;

    private static final SAXParserFactory SAX_PARSER_FACTORY = SAXParserFactory.newInstance();

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Medicine getDrugList() {
        return drugList;
    }

    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema. With
     *            this parameter it is possible make parser validating.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {
        SAX_PARSER_FACTORY.setNamespaceAware(true);

        if(validate){
            SAX_PARSER_FACTORY.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
            SAX_PARSER_FACTORY.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        SAXParser parser = SAX_PARSER_FACTORY.newSAXParser();
        parser.parse(xmlFileName, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = localName;

        if(XML.MEDICINE.equalsTo(currentElement)){
            drugList = new Medicine();
            return;
        }

        if(XML.DRUG.equalsTo(currentElement)){
            drug = new Drug();
            return;
        }

        if(XML.VERSION.equalsTo(currentElement)){
            version = new Version();
            return;
        }

        if(XML.CERTIFICATE.equalsTo(currentElement)){
            certificate = new Certificate();
            certificate.setNumber(Integer.parseInt(attributes.getValue(uri, XML.NUMBER.value())));
            certificate.setCreated(Integer.parseInt(attributes.getValue(uri, XML.CREATED.value())));
            certificate.setTill(Integer.parseInt(attributes.getValue(uri, XML.TILL.value())));
            certificate.setOrganization(attributes.getValue(uri, XML.ORGANIZATION.value()));
            return;
        }

        if(XML.PACKAGE.equalsTo(currentElement)){
            aPackage = new Package();
            aPackage.setType(attributes.getValue(uri, XML.PACKTYPE.value()));
            aPackage.setAmount(Integer.parseInt(attributes.getValue(uri, XML.AMOUNT.value())));
            aPackage.setPrice(Double.parseDouble(attributes.getValue(uri, XML.PRICE.value())));
            return;
        }

        if(XML.DOSAGE.equalsTo(currentElement)){
            dosage = new Dosage();
            dosage.setAmount(Double.parseDouble(attributes.getValue(uri, XML.DOSAGEAMOUNT.value())));
            dosage.setPeriodicity(Integer.parseInt(attributes.getValue(uri, XML.PERIODICITY.value())));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String elementText = new String(ch, start, length).trim();

        if(elementText.isEmpty()){
            return;
        }
        if(XML.NAME.equalsTo(currentElement)){
            drug.setName(elementText);
            return;
        }
        if(XML.PHARM.equalsTo(currentElement)){
            drug.setPharmName(elementText);
            return;
        }
        if(XML.GROUP.equalsTo(currentElement)){
            drug.setGroup(elementText);
            return;
        }
        if(XML.ANALOGS.equalsTo(currentElement)){
            drug.setAnalogs(elementText);
            return;
        }
        if(XML.TYPE.equalsTo(currentElement)){
            version.setType(elementText);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if(XML.VERSION.equalsTo(localName)){
            drug.setVersion(version);
        }
        if(XML.CERTIFICATE.equalsTo(localName)){
            version.setCertificate(certificate);
        }
        if(XML.DOSAGE.equalsTo(localName)){
            version.setDosage(dosage);
        }
        if(XML.PACKAGE.equalsTo(localName)){
            version.setPackageType(aPackage);
        }
        if(XML.DRUG.equalsTo(localName)){
            drugList.getMedicineList().add(drug);
        }
    }
}
