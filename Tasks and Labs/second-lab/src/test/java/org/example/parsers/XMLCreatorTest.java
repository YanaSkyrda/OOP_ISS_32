package org.example.parsers;

import org.apache.commons.io.FileUtils;
import org.example.entity.Drug;
import org.example.entity.Group;
import org.example.entity.Version;
import org.example.util.XMLCreator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XMLCreatorTest {
    @Test
    public void createXML(){
        XMLCreator xmlCreator = new XMLCreator();
        List<Drug> drugs = new ArrayList<>();
        Drug drug1 = new Drug();
        Drug drug2 = new Drug();
        drug1.setVersion(new Version());
        drug2.setVersion(new Version());

        drug1.setName("Citramon");
        drug1.setId(13726);
        drug1.setPharmName("Darnitsa");
        drug1.setGroup(Group.Sedative);
        drug1.setAnalogs("None");
        drug1.getVersion().setType("Tablets");
        drug1.getVersion().setCertificate(2020);
        drug1.getVersion().setPackageType("plastic");
        drug1.getVersion().setDosage(1.5);

        drug2.setName("Pharmacetron");
        drug2.setId(23973283);
        drug2.setPharmName("AgeNow");
        drug2.setGroup(Group.Antibiotics);
        drug2.setAnalogs("Nimesil");
        drug2.getVersion().setType("Mixtures");
        drug2.getVersion().setCertificate(2024);
        drug2.getVersion().setPackageType("tube");
        drug2.getVersion().setDosage(5.0);

        drugs.add(drug1);
        drugs.add(drug2);
        xmlCreator.buildXML(drugs,"src/test/resources/XMLCreatorTest.xml");
        try {
            Assert.assertTrue(FileUtils.contentEquals(new File("src/test/resources/XMLCreatorTest.xml"),
                    new File("src/test/resources/expected.xml")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}