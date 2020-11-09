package org.example.parsers;

import org.example.entity.Drug;
import org.example.entity.Group;
import org.example.entity.Version;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class DrugHandlerTest {
    private final List<Drug> drugs = new ArrayList<>();

    @Before
    public void setUp(){
        Drug drug1 = new Drug();
        drug1.setVersion(new Version());
        drug1.setName("Citramon");
        drug1.setId(13726);
        drug1.setPharmName("Darnitsa");
        drug1.setGroup(Group.Sedative);
        drug1.setAnalogs("None");
        drug1.getVersion().setType("Tablets");
        drug1.getVersion().setCertificate(2020);
        drug1.getVersion().setPackageType("plastic");
        drug1.getVersion().setDosage(1.5);
        drugs.add(drug1);
    }

    @Test
    public void testHandlerWithDOM(){
        DOMParser domParser = new DOMParser();
        domParser.parse("src/test/resources/XMLCreatorTest.xml");
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getName(), drugs.get(0).getName());
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getPharmName(), drugs.get(0).getPharmName());
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getId(), drugs.get(0).getId());
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getAnalogs(), drugs.get(0).getAnalogs());
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getGroup(), drugs.get(0).getGroup());
        Assert.assertEquals(domParser.drugHandler.getMedicine().getDrugList().get(0).getVersion(), drugs.get(0).getVersion());
    }
}