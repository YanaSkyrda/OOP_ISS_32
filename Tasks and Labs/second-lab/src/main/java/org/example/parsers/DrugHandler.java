package org.example.parsers;

import org.example.entity.Drug;
import org.example.entity.Group;
import org.example.entity.Medicine;
import org.example.entity.Version;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.List;

public class DrugHandler extends DefaultHandler {
    private static final String MEDICINE = "Medicine";
    private static final String DRUG = "Drug";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String PHARM = "Pharm";
    private static final String GROUP = "Group";
    private static final String ANALOGS = "Analogs";
    private static final String VERSION = "Version";
    private static final String TYPE = "Type";
    private static final String CERTIFICATE = "Certificate";
    private static final String PACKAGE = "Package";
    private static final String DOSAGE = "Dosage";

    private Medicine drugList = new Medicine();

    private String elementValue;

    private final String nodeName = "Drug";

    public void setElementValue(String elementValue) {
        this.elementValue = elementValue;
    }

    public String getName() {
        return nodeName;
    }

    @Override
    public void characters(char[] ch, int start, int length){
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() {
        drugList = new Medicine();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr){
        switch (qName) {
            case DRUG:
                Drug drug = new Drug();
                drugList.getDrugList().add(drug);
                break;
            case VERSION:
                lastestDrug().setVersion(new Version());
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        setField(qName);
    }

    public Drug lastestDrug() {
        List<Drug> gunList = drugList.getDrugList();
        int latestArticleIndex = gunList.size() - 1;
        return gunList.get(latestArticleIndex);
    }

    public Medicine getMedicine() {
        return drugList;
    }

    public void setField(String qName) {
        switch (qName) {
            case ID:
                lastestDrug().setId(Integer.parseInt(elementValue));
                break;
            case NAME:
                lastestDrug().setName(elementValue);
                break;
            case PHARM:
                lastestDrug().setPharmName(elementValue);
                break;
            case GROUP:
                lastestDrug().setGroup(Group.valueOf(elementValue));
                break;
            case ANALOGS:
                lastestDrug().setAnalogs(elementValue);
                break;
            case TYPE:
                lastestDrug().getVersion().setType(elementValue);
                break;
            case CERTIFICATE:
                lastestDrug().getVersion().setCertificate(Integer.parseInt(elementValue));
                break;
            case PACKAGE:
                lastestDrug().getVersion().setPackageType(elementValue);
                break;
            case DOSAGE:
                lastestDrug().getVersion().setDosage(Double.parseDouble(elementValue));
                break;
            default:
                break;
        }
    }

    public void setField(String qName, String attribute){
        switch (qName) {
            case DRUG:
                Drug drug = new Drug();
                drugList.getDrugList().add(drug);
                break;
            case VERSION:
                lastestDrug().setVersion(new Version());
                break;
            default:
                setField(qName);
                break;
        }
    }
}
