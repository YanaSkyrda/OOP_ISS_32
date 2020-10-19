package org.example.controllers;

import org.example.entity.*;
import org.example.entity.Package;
import org.example.util.XML;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.util.Objects;

public class STAXController {

    private final String xmlFileName;

    private Medicine drugList;

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Medicine getDrugList() {
        return drugList;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws XMLStreamException {
        Drug drug = null;
        Version version = null;
        Certificate certificate = null;
        Dosage dosage = null;
        Package aPackage = null;
        String currentElement = null;


        XML_INPUT_FACTORY.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = XML_INPUT_FACTORY.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()){
            XMLEvent event = reader.nextEvent();

            if(event.isCharacters() && event.asCharacters().isWhiteSpace()){
                continue;
            }

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if(XML.MEDICINE.equalsTo(currentElement)){
                    drugList = new Medicine();
                    continue;
                }

                if(XML.DRUG.equalsTo(currentElement)){
                    drug = new Drug();
                    continue;
                }

                if(XML.VERSION.equalsTo(currentElement)){
                    version = new Version();
                    continue;
                }

                if(XML.CERTIFICATE.equalsTo(currentElement)){
                    certificate = new Certificate();
                    certificate.setNumber(Integer.parseInt(startElement.getAttributeByName(new QName(XML.NUMBER.value())).getValue()));
                    certificate.setCreated(Integer.parseInt(startElement.getAttributeByName(new QName(XML.CREATED.value())).getValue()));
                    certificate.setTill(Integer.parseInt(startElement.getAttributeByName(new QName(XML.TILL.value())).getValue()));
                    certificate.setOrganization(startElement.getAttributeByName(new QName(XML.ORGANIZATION.value())).getValue());
                    continue;
                }

                if(XML.PACKAGE.equalsTo(currentElement)){
                    aPackage = new Package();
                    aPackage.setType(startElement.getAttributeByName(new QName(XML.PACKTYPE.value())).getValue());
                    aPackage.setAmount(Integer.parseInt(startElement.getAttributeByName(new QName(XML.AMOUNT.value())).getValue()));
                    aPackage.setPrice(Double.parseDouble(startElement.getAttributeByName(new QName(XML.PRICE.value())).getValue()));
                    continue;
                }

                if(XML.DOSAGE.equalsTo(currentElement)){
                    dosage = new Dosage();
                    dosage.setAmount(Double.parseDouble(startElement.getAttributeByName(new QName(XML.DOSAGEAMOUNT.value())).getValue()));
                    dosage.setPeriodicity(Integer.parseInt(startElement.getAttributeByName(new QName(XML.PERIODICITY.value())).getValue()));
                }
            }

            if(event.isCharacters()){
                Characters characters = event.asCharacters();

                if(XML.NAME.equalsTo(currentElement)){
                    Objects.requireNonNull(drug).setName(characters.getData());
                    continue;
                }

                if(XML.PHARM.equalsTo(currentElement)){
                    Objects.requireNonNull(drug).setPharmName(characters.getData());
                    continue;
                }

                if(XML.GROUP.equalsTo(currentElement)){
                    Objects.requireNonNull(drug).setGroup(characters.getData());
                    continue;
                }

                if(XML.ANALOGS.equalsTo(currentElement)){
                    Objects.requireNonNull(drug).setAnalogs(characters.getData());
                    continue;
                }

                if(XML.TYPE.equalsTo(currentElement)){
                    Objects.requireNonNull(version).setType(characters.getData());
                    continue;
                }
            }

            if(event.isEndElement()){
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if(XML.CERTIFICATE.equalsTo(localName)){
                    Objects.requireNonNull(version).setCertificate(certificate);
                }

                if(XML.PACKAGE.equalsTo(localName)){
                    Objects.requireNonNull(version).setPackageType(aPackage);
                }

                if(XML.DOSAGE.equalsTo(localName)){
                    Objects.requireNonNull(version).setDosage(dosage);
                }

                if(XML.VERSION.equalsTo(localName)){
                    Objects.requireNonNull(drug).setVersion(version);
                }

                if(XML.DRUG.equalsTo(localName)){
                    drugList.getMedicineList().add(drug);
                }
            }
        }
        reader.close();
    }
}

