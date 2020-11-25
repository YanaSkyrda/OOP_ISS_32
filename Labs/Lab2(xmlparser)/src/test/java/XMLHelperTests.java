import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.Test;
import parsers.XMLHelper;
import targetclasses.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class XMLHelperTests {
    Orangery orangery = new Orangery();

    void createOrangery() {
        for (int i = 0; i < 3; i++) {
            Flower flower = new Flower();
            flower.setVisualParameters(new VisualParameters());
            flower.setGrowingTips(new GrowingTips());

            flower.setCode(BigInteger.valueOf(125 + i));
            flower.setName("flower" + i);
            flower.setSoil(Soil.values()[i % Soil.values().length]);
            flower.setOrigin(Country.values()[i % Country.values().length]);
            flower.setMultiplying(Multiplying.values()[i % Multiplying.values().length]);

            flower.getVisualParameters().setAverageSize(34 + i);
            flower.getVisualParameters().setStalkColor(
                    StalkColor.values()[i % StalkColor.values().length]);
            flower.getVisualParameters().setLeavesColor(
                    LeavesColor.values()[i % LeavesColor.values().length]);

            flower.getGrowingTips().setTemperature(BigInteger.valueOf(i * 3));
            flower.getGrowingTips().setWatering(35 + i);
            flower.getGrowingTips().setLightloving(true);
            orangery.getFlower().add(flower);
        }
    }

    @Test
    void shouldBuildSameXMLFileAsExpectedXMLFile() {
        //Given
        createOrangery();
        //When
        XMLHelper.build(orangery,"src\\test\\java\\res\\buildResult.xml");
        //Then
        try {
            assert(FileUtils.contentEquals(new File("src\\test\\java\\res\\buildResult.xml"),
                    new File("src\\test\\java\\res\\expected.xml")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void shouldValidateFile() {
        assert(XMLHelper.isValid("src\\test\\java\\res\\expected.xml",
                "src\\main\\resources\\orangery.xsd"));
    }

    @Test
    void shouldNotValidateFileBecauseOfMissingTag() {
        assert(!XMLHelper.isValid("src\\test\\java\\res\\invalidBecauseOfMissingTag.xml",
                "src\\main\\resources\\orangery.xsd"));
    }

    @Test
    void shouldNotValidateFileBecauseOfWrongEnumName() {
        assert(!XMLHelper.isValid("src\\test\\java\\res\\invalidBecauseOfWrongEnumName.xml",
                "src\\main\\resources\\orangery.xsd"));
    }

    @Test
    void shouldNotValidateFileBecauseOfWrongOrder() {
        assert(!XMLHelper.isValid("src\\test\\java\\util\\invalidBecauseOfWrongOrder.xml",
                "src\\main\\resources\\orangery.xsd"));
    }
}
