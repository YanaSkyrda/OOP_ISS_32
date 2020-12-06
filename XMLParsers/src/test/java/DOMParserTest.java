import model.Candy;
import model.Types;
import model.XMLElements;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import parsers.DOMParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DOMParserTest {


    @Test
    void parse() throws NullPointerException {
        DOMParser parser = new DOMParser();
        File file = new File("src/main/resources/candy.xml");
        List<Candy> candies = parser.parse(file);
        Candy candy = candies.get(0);
        assertEquals(candy.getValue().getCarbohydrates(), 50);
        assertEquals(candy.getValue().getProteins(), 50);
        assertEquals(candy.getValue().getFats(), 50);
        assertEquals(XMLElements.IDOfFirstCandy, candy.getId());
        assertEquals(XMLElements.NameOfFirstCandy, candy.getName());
        assertEquals(50, candy.getEnergy());
        assertTrue(candy.getTypes().contains(Types.CHOCOLATE));
        assertEquals(candy.getComponents().get(0).getAmount(),10);
        assertEquals(candy.getComponents().get(0).getTypeOfMeasure(),"g");
        assertEquals(candy.getComponents().get(0).getName(), "Salt");

        Candy candy2 = candies.get(1);

        assertEquals(candy2.getValue().getCarbohydrates(), 50);
        assertEquals(candy2.getValue().getProteins(), 50);
        assertEquals(candy2.getValue().getFats(), 50);
        assertEquals(XMLElements.IDOfSecondCandy, candy2.getId());
        assertEquals(XMLElements.NameOfSecondCandy, candy2.getName());
        assertEquals(50, candy2.getEnergy());
        assertTrue(candy2.getTypes().contains(Types.CHOCOLATE));
        assertEquals(candy2.getComponents().get(0).getAmount(),10);
        assertEquals(candy2.getComponents().get(0).getTypeOfMeasure(),"g");
        assertEquals(candy2.getComponents().get(0).getName(), "Salt");
    }
}
