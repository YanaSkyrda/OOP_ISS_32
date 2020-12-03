import model.Candy;
import model.Types;
import org.junit.jupiter.api.Test;
import parsers.DOMParser;
import parsers.StAXParser;
import parsers.XMLSAXParser;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StAXParserTest {

    @Test
    void parse() throws NullPointerException, FileNotFoundException, XMLStreamException {
        StAXParser parser = new StAXParser();
        File file = new File("/Users/mykolamedynsky/Desktop/oop-java/OOP_ISS_32/XMLParsers/src/main/resources/candy.xml");
        List<Candy> candies = parser.parse(file);
        Candy candy = candies.get(0);
        assertEquals(candy.getValue().getCarbohydrates(), 50);
        assertEquals(candy.getValue().getProteins(), 50);
        assertEquals(candy.getValue().getFats(), 50);
        assertEquals("lol", candy.getId());
        assertEquals("Kek", candy.getName());
        assertEquals(50, candy.getEnergy());
        assertTrue(candy.getTypes().contains(Types.CHOCOLATE));
        assertEquals(candy.getComponents().get(0).getAmount(),10);
        assertEquals(candy.getComponents().get(0).getTypeOfMeasure(),"g");
        assertEquals(candy.getComponents().get(0).getName(), "Salt");
    }
}
